## Android Hotel Reservation App

### Screen 1

Input Form with fields:

-   Guest Name
-   Number of Guests
-   Check-in Date
-   Check-out Date

Data Validation will be run when user clicks on the "Search" button. If there are any errors, the user will be shown an error message. If there are no errors, the user will be taken to the next screen.

Data Validation Rules:

-   Guest Name: Required and must be at least 3 characters
-   Number of Guests: Required and must be a positive number
-   Check-in Date: Required and must be on or after today
-   Check-out Date: Required and must be after the check-in date

### Screen 2

Display the the information entered in Screen 1.

Show a list of mock hotels using RecyclerView. Each item in the list should show the following information:

-   name
-   price
-   availability
