import axios from "axios";
import CryptoJS from "crypto-js";

export default class ApiService {

    // static BASE_URL = "http://localhost:5001";
    static MAIN_BASE_URL = "http://107.175.82.41"
    static BASE_URL = `${ApiService.MAIN_BASE_URL}/api/v1`;
    static ENCRYPTION_KEY = "dunky-secrete-key";
    static IMAGE_BASE_URL = `${ApiService.MAIN_BASE_URL}/images`;

    //enctyp token using cryptojs

    static encrypt(token) {
        return CryptoJS.AES.encrypt(token, this.ENCRYPTION_KEY.toString());
    }

    //deceype token using cryptojs
    static decrypt(token) {
        const bytes = CryptoJS.AES.decrypt(token, this.ENCRYPTION_KEY);
        return bytes.toString(CryptoJS.enc.Utf8);
    }

    //save token
    static saveToken(token) {
        const encrytpedToken = this.encrypt(token);
        localStorage.setItem("token", encrytpedToken);
    }

    //retreive token
    static getToken() {
        const encrytpedToken = localStorage.getItem("token");
        if (!encrytpedToken) return null;
        return this.decrypt(encrytpedToken)
    }

    //save role
    static saveRole(role) {
        const encrytpedRole = this.encrypt(role);
        localStorage.setItem("role", encrytpedRole);
    }


    //get role
    static getRole() {
        const encrytpedRole = localStorage.getItem("role");
        if (!encrytpedRole) return null;
        return this.decrypt(encrytpedRole)
    }

    static clearAuth() {
        localStorage.removeItem("token");
        localStorage.removeItem("role");
    }

    static getHeader() {
        const token = this.getToken();
        return {
            Authorization: `Bearer ${token}`,
            "Content-Type": "application/json"
        }
    }

    /** AUTH AND USERS API METHODS */

    // AUTH
    static async registerUser(registrationData) {
        const resp = await axios.post(`${this.BASE_URL}/auth`, registrationData);
        return resp.data;
    }


    static async loginUser(loginData) {
        const resp = await axios.post(`${this.BASE_URL}/auth/sessions`, loginData);
        return resp.data;
    }

    // USERS
    static async myProfile() {
        const resp = await axios.get(`${this.BASE_URL}/users/me`, {
            headers: this.getHeader()
        })
        return resp.data;
    }

    static async myBookings() {
        const resp = await axios.get(`${this.BASE_URL}/users/me/bookings`, {
            headers: this.getHeader()
        })
        return resp.data;
    }

    static async deleteAccount() {
        const resp = await axios.delete(`${this.BASE_URL}/users`, {
            headers: this.getHeader()
        })
        return resp.data;
    }

    // ROOMS

    static async addRoom(formData) {
        const resp = await axios.post(`${this.BASE_URL}/rooms`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return resp.data;
    }

    //to get room types
    static async getRoomTypes() {
        const resp = await axios.get(`${this.BASE_URL}/rooms/types`, {
            headers: this.getHeader()
        });
        return resp.data;
    }

    //to get all rooms
    static async getAllRooms() {
        const resp = await axios.get(`${this.BASE_URL}/rooms`, {
            headers: this.getHeader()
        });
        return resp.data;
    }

    //To get room details
    static async getRoomById(roomId) {
        const resp = await axios.get(`${this.BASE_URL}/rooms/${roomId}`);
        return resp.data;
    }

    static async deleteRoom(roomId) {
        const resp = await axios.delete(`${this.BASE_URL}/rooms/${roomId}`, {
            headers: this.getHeader()
        });
        return resp.data;
    }

    static async updateRoom(formData) {
        const resp = await axios.put(`${this.BASE_URL}/rooms`, formData, {
            headers: {
                ...this.getHeader(),
                'Content-Type': 'multipart/form-data'
            }
        });
        return resp.data;
    }

    static async getAvailableRooms(checkInDate, checkOutDate, roomType) {
        
        const resp = await axios.get(`${this.BASE_URL}/rooms/available?checkInDate=${checkInDate}&checkOutDate=${checkOutDate}&roomType=${roomType}`);
        return resp.data;

    }


    //BOOKINGS
    static async getBookingByReference(bookingCode) {
        const resp = await axios.get(`${this.BASE_URL}/bookings/${bookingCode}`);
        return resp.data;
    }

    static async bookRoom(booking) {
        const resp = await axios.post(`${this.BASE_URL}/bookings`, booking, {
            headers: this.getHeader()
        });
        return resp.data;
    }

    static async getAllBookings() {
        const resp = await axios.get(`${this.BASE_URL}/bookings`, {
            headers: this.getHeader()
        });
        return resp.data;
    }

    static async updateBooking(booking) {
        const resp = await axios.put(`${this.BASE_URL}/bookings`, booking, {
            headers: this.getHeader()
        });
        return resp.data;
    }

    //PAYMMENT 
    //funtion to create payment intent
    static async proceedForPayment(body) {
        const resp = await axios.post(`${this.BASE_URL}/payments/pay`, body, {
            headers: this.getHeader()
        });
        return resp.data; //return the strip transaction id for this transaction
    }

    //TO UPDATE PAYMENT WHEN IT HAS BEEN COMPLETED
    static async updateBookingPaymeent(body) {
        const resp = await axios.put(`${this.BASE_URL}/payments/update`, body, {
            headers: this.getHeader()
        });
        return resp.data;
    }



    //AUTHENTICATION CHECKER
    static logout(){
        this.clearAuth();
    }

    static isAthenticated(){
        const token = this.getToken();
        return !!token;
    }

    static isAdmin(){
        const role = this.getRole();
        return role === "ADMIN";
    }

    static isCustomer(){
        const role = this.getRole();
        return role === "CUSTOMER";
    }



}