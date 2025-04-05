package com.dunky.cs489.unitedroyal.util;

import com.dunky.cs489.unitedroyal.model.Apartment;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;
import java.util.stream.Collectors;

public class JsonUtil {
    public static String convertApartmentsToJson(List<Apartment> apartments) {
        JSONArray array = new JSONArray(
                apartments.stream().map(a -> {
                    JSONObject obj = new JSONObject();
                    obj.put("apartmentNo", a.apartmentNo());
                    obj.put("propertyName", a.propertyName());
                    obj.put("floorNo", a.floorNo());
                    obj.put("size", a.size());
                    obj.put("numberOfRooms", a.numberOfRooms());

                    JSONArray leaseArray = new JSONArray(
                            a.leases().stream().map(l -> {
                                JSONObject leaseObj = new JSONObject();
                                leaseObj.put("leaseNo", l.leaseNo());
                                leaseObj.put("startDate", l.startDate());
                                leaseObj.put("endDate", l.endDate());
                                leaseObj.put("monthlyRentalRate", l.monthlyRentalRate());
                                leaseObj.put("tenant", l.tenant());
                                leaseObj.put("apartmentNo", l.apartmentNo());
                                return leaseObj;
                            }).collect(Collectors.toList())
                    );

                    obj.put("leases", leaseArray);
                    return obj;
                }).collect(Collectors.toList())
        );

        return array.toString(2);
    }
}
