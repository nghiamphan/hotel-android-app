## Android Hotel Reservation App

### Screen 1

Input Form with fields:

-   Guest Name
-   Number of Guests
-   Check-in Date
-   Check-out Date

Data Validation will be run when user clicks on the "Search" button. If there are any errors, the user will be shown an error message. If there are no errors, the input values will be saved to SharedPreferences and the user will be taken to the next screen.

Data Validation Rules:

-   Guest Name: Required and must be at least 3 characters
-   Number of Guests: Required and must be a positive number
-   Check-in Date: Required and must be on or after today
-   Check-out Date: Required and must be after the check-in date

### Screen 2

Display the the information entered in Screen 1.

Show a list of hotels fetched from the backend using RecyclerView. Each item in the list should show the following information:

-   name
-   address
-   price
-   availability

User can choose from one of the available hotels. When the user clicks on a hotel, the user will be taken to the next screen and the hotel information will be saved to bundle to pass to the next screen (entire hotel object will be saved to bundle using Parcelable).

### Screen 3

Display the information passed from Screen 1 as well as the hotel information passed from Screen 2.

For each guest, show the input form to enter the following information:

-   Name
-   Age
-   Gender (drop-down element)

Form validation:

-   Name: Required and must be at least 3 characters
-   Age: Required and must be a positive number from 1 to 99
-   Gender: Required

When the user clicks the "Reserve" button, a POST request will be sent to the backend. If the request is successful, the user will be taken to the next screen.

### Screen 4

There is a confirmation message that the reservation was successful that contains the reservation number, which is the id of the reservation returned from the backend.
