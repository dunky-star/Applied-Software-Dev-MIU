-- Display the list of ALL Customers registered in the system, sorted in ascending
-- order of their lastNames
SELECT *
FROM users
WHERE role = 'CUSTOMER'
ORDER BY last_name ASC;


-- Display ALL bookings for a given customer by their booking_id, including room info
SELECT b.*, r.room_number, r.room_type, r.price_per_night, r.capacity, r.description
FROM bookings b
         JOIN rooms r ON b.room_id = r.id
WHERE b.id = 5;


-- Display ALL bookings that are currently CANCELED
SELECT *
FROM bookings
WHERE booking_status = 'CANCELED';

-- Display Top 10 Paying Customers including payment details and notification to be used for Loyalty promotion program
SELECT
    u.email,
    u.first_name,
    u.last_name,
    b.booking_status,
    p.payment_method,
    p.status AS payment_status,
    n.notification_type,
    SUM(p.amount) OVER (PARTITION BY u.id) AS total_paid
FROM users u
         JOIN bookings b ON u.id = b.user_id AND b.booking_status != 'CANCELED'
         JOIN payments p ON u.id = p.user_id AND p.status = 'CONFIRMED'
         LEFT JOIN rooms r ON b.room_id = r.id
         LEFT JOIN notifications n ON b.id = n.booking_id
ORDER BY total_paid DESC
LIMIT 10;
