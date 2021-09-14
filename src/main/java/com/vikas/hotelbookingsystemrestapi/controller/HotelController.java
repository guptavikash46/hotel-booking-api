package com.vikas.hotelbookingsystemrestapi.controller;

import com.vikas.hotelbookingsystemrestapi.config.SecurityConfig;
import com.vikas.hotelbookingsystemrestapi.entity.Hotel;
import com.vikas.hotelbookingsystemrestapi.entity.User;
import com.vikas.hotelbookingsystemrestapi.exceptionHandling.CustomError;
import com.vikas.hotelbookingsystemrestapi.service.HotelService;
import com.vikas.hotelbookingsystemrestapi.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
public class HotelController implements Serializable {
    @Autowired
    private HotelService hotelService;
    @Autowired
    private UserService userService;
    @Autowired
    private SecurityConfig securityConfig;
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, String> errorMsg = new HashMap<>();

    @GetMapping("/")
    public String helloAdmin() {
        return "hello admin";
    }

    @GetMapping("/user")
    public Map<String, String> user() {
        Map<String, String> result = new HashMap<>();
        result.put("msg", "Welcome user!");
        return result;
    }
    @PostMapping("/add-hotel")
    public ResponseEntity<Object> addHotel(@RequestBody Hotel hotel) {
        Optional<Hotel> hotelObj = Optional.of(hotel);
        int saveStatus;
        if (!hotelObj.isEmpty()) {
            //to convert string format date into Date
//            LocalDate currentDate = LocalDate.parse(obj.getDate().toString(),
//                    DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            Hotel newHotel = new Hotel(hotelObj.get().getName(), hotelObj.get().getCity(),
                                        hotelObj.get().getDate(), hotelObj.get().getNumberOfGuests(),
                                        hotelObj.get().getAddress(), hotelObj.get().getTotalRooms(),
                                        hotelObj.get().getRoomsAvailable(), hotelObj.get().getCostPerNight(),
                                        hotelObj.get().getEmail(), hotelObj.get().getPhone(),
                                        hotelObj.get().getHasWifi(), hotelObj.get().getHasRestaurant(),
                                        hotelObj.get().getHasAC(), hotelObj.get().getHasMeal(),
                                        hotelObj.get().getStars());

            logger.info("Hotel meal val: "+newHotel.getHasMeal());
            saveStatus = hotelService.save(newHotel);
            if (saveStatus == 1) {
                return new ResponseEntity<>("Hotel added!", HttpStatus.CREATED);
            }
        }
        return new ResponseEntity<>("Error while adding the hotel", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<Object> findOne(@PathVariable("id") Long id) {
        Hotel hotelData = hotelService.findOneHotel(id);
        Optional<Hotel> hotelObj = Optional.of(hotelData);
        return new ResponseEntity<>(hotelObj.get(), HttpStatus.OK);
    }

    @PostMapping("/add-guest/{id}")
    public ResponseEntity<Object> addUserGuest(@RequestBody User user, @PathVariable("id") Long hotelId) {
        Optional<User> usrObj = Optional.of(user);
        int result = -1;
        if (!usrObj.isEmpty()) {
            Hotel selectedHotel = hotelService.findOneHotel(hotelId);
            User guest = new User(user.getName(), user.getEmail(), user.getGender(), user.getResidentialCity(),
                    user.getRatingDescription(), user.getRating(), selectedHotel);
            result = userService.save(guest);

        }
        //now result based return
        if (result == 1){
            errorMsg.put("Message: ", "Guest added successfully!");
            return new ResponseEntity<>(errorMsg, HttpStatus.CREATED);
        }
        errorMsg.clear();
        errorMsg.put("Message: ", "Unable to add new guest, please check all values.");
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/search-hotel")
    public ResponseEntity<Object> searchHotel(@RequestParam("city")String city,
                                              @RequestParam("date") String date,
                                              @RequestParam(value = "travellerCount") Integer travellerCount,
                                              @RequestParam(value = "stars", required = false) String stars,
                                              @RequestParam(value = "wifi", required = false) Boolean wifi,
                                              @RequestParam(value = "restaurant", required = false) Boolean restaurant,
                                              @RequestParam(value = "ac", required = false) Boolean ac,
                                              @RequestParam(value = "meals", required = false) Boolean meals )
                                               {

        try {
//            List<Hotel> hotels = hotelService.searchHotels(city, date,travellerCount, stars, wifi, restaurant,
//                    ac, meals);
            List<Hotel> hotels = hotelService.searchHotels(city, date,travellerCount);
            return new ResponseEntity<>(hotels, HttpStatus.OK);
        }
        catch (CustomError e) {}
        errorMsg.clear();
        errorMsg.put("Mesage:", "Unable to search the hotel, check the values provided");
        return new ResponseEntity<>(errorMsg, HttpStatus.BAD_REQUEST);
    }
}
