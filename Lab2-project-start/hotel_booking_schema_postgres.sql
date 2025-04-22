-- Disable referential integrity temporarily
SET session_replication_role = replica;

DROP TABLE IF EXISTS
    notifications,
    bookings,
    booking_references,
    rooms,
    users,
    payments
    CASCADE;

-- Re-enable referential integrity
SET session_replication_role = DEFAULT;

-- USERS
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(25),
    last_name VARCHAR(25),
    email VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255),
    phone_number VARCHAR(20) UNIQUE,
    role VARCHAR(255) CHECK (role IN ('CUSTOMER', 'ADMIN')),
    is_active BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- ROOMS
CREATE TABLE rooms (
    id BIGSERIAL PRIMARY KEY,
    room_number VARCHAR(50) UNIQUE,
    room_type VARCHAR(255) CHECK (room_type IN ('SINGLE', 'DOUBLE', 'SUITE')),
    price_per_night NUMERIC(19,2),
    capacity INTEGER,
    description VARCHAR(255),
    is_available BOOLEAN DEFAULT TRUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- BOOKING_REFERENCES
CREATE TABLE booking_references (
    id BIGSERIAL PRIMARY KEY,
    reference_code VARCHAR(255) UNIQUE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- PAYMENTS
CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    amount NUMERIC(19,2),
    transaction_id VARCHAR(255),
    payment_method VARCHAR(255) CHECK (
        payment_method IN ('PAYPAL', 'STRIPE', 'VISA', 'MASTER_CARD', 'AMERICAN_EXPRESS', 'DISCOVER', 'MTN_MOBILE_MONEY', 'CASH_ON_DELIVERY')
    ),
    payment_date DATE,
    status VARCHAR(255) CHECK (
        status IN ('CONFIRMED', 'CANCELED', 'PENDING', 'FAILED', 'DECLINED')
    ),
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- BOOKINGS
CREATE TABLE bookings (
    id BIGSERIAL PRIMARY KEY,
    check_in_date DATE,
    check_out_date DATE,
    total_price NUMERIC(19,2),
    booking_status VARCHAR(25) CHECK (
        booking_status IN ('BOOKED', 'CONFIRMED', 'CANCELED', 'PENDING', 'CHECKED_IN', 'CHECKED_OUT')
    ),
    payment_status VARCHAR(25) CHECK (
        payment_status IN ('CONFIRMED', 'CANCELED', 'PENDING', 'FAILED', 'DECLINED')
    ),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    reference_code VARCHAR(255) REFERENCES booking_references(reference_code) ON DELETE CASCADE,
    room_id BIGINT REFERENCES rooms(id) ON DELETE CASCADE,
    payment_id BIGINT REFERENCES payments(id) ON DELETE CASCADE
);

-- NOTIFICATIONS
CREATE TABLE notifications (
    id BIGSERIAL PRIMARY KEY,
    subject VARCHAR(255),
    recipient_email VARCHAR(255),
    body VARCHAR(255),
    notification_type VARCHAR(50) CHECK (
        notification_type IN ('EMAIL', 'SMS', 'WHATSAPP', 'PUSH_NOTIFICATION', 'IN_APP_NOTIFICATION')
    ),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    booking_id BIGINT REFERENCES bookings(id) ON DELETE CASCADE
);

-- Index for performance on bookings by reference_code
CREATE INDEX idx_reference_code ON bookings(reference_code);
