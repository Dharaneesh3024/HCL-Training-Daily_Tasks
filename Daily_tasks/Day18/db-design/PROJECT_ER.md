# Car Rental System - ER Diagram

!\[Car rental ER diagram](diagrams/project\_er.png)

## Entities

|Entity|Purpose|Requirement|
|-|-|-|
|BRANCH|Rental location|FR1|
|STAFF|Employee who works at a branch and records pickups and returns|FR4, FR5|
|VEHICLE|Car in the fleet, belongs to one branch|FR1, FR2|
|CUSTOMER|Person who books cars|FR2, FR3|
|DRIVING\_LICENCE|Licence details uploaded by the customer before booking|FR3|
|BOOKING|Rental of one vehicle for a date range|FR2, FR3, FR6|
|PICKUP|Odometer and fuel recorded when the car leaves|FR4|
|VEHICLE\_RETURN|Odometer, fuel and inspection notes recorded when the car comes back|FR5|
|EXTRA\_CHARGE|Late, fuel or damage charges added to a booking|FR6|
|MAINTENANCE\_BLOCK|Date range when a vehicle is unavailable|FR7|

## Relationships and cardinalities

|Relationship|Cardinality|Foreign key|
|-|-|-|
|Branch houses Vehicle|1 : N|vehicle.branch\_id|
|Branch employs Staff|1 : N|staff.branch\_id|
|Customer submits Driving licence|1 : 1|driving\_licence.customer\_id (unique)|
|Customer makes Booking|1 : N|booking.customer\_id|
|Vehicle is booked in Booking|1 : N|booking.vehicle\_id|
|Vehicle is blocked by Maintenance block|1 : N|maintenance\_block.vehicle\_id|
|Booking starts with Pickup|1 : 0..1|pickup.booking\_id (unique)|
|Booking ends with Return|1 : 0..1|vehicle\_return.booking\_id (unique)|
|Booking may incur Extra charge|1 : N|extra\_charge.booking\_id|
|Staff records Pickup|1 : N|pickup.staff\_id|
|Staff records Return|1 : N|vehicle\_return.staff\_id|

## Keys

|Table|Primary key|Unique constraints|
|-|-|-|
|branch|branch\_id|-|
|staff|staff\_id|-|
|vehicle|vehicle\_id|plate|
|customer|customer\_id|email|
|driving\_licence|licence\_id|customer\_id, licence\_number|
|booking|booking\_id|-|
|pickup|pickup\_id|booking\_id|
|vehicle\_return|return\_id|booking\_id|
|extra\_charge|charge\_id|-|
|maintenance\_block|maintenance\_id|-|

Generated integer ids are used as primary keys because plates, emails and licence numbers can change or be corrected. Business identifiers are kept unique instead.

## Design decisions

|Decision|Reason|
|-|-|
|Pickup and return are separate tables|They happen at different times, a booking may not have either yet, and each has its own columns, so there are no empty columns in booking.|
|Extra charges are a separate table|A booking can have any number of charges, which would be a repeating group inside booking.|
|Licence is separate from customer|Licence number, expiry and document path depend on the licence, not on the customer.|
|Utilisation percentage is not stored|It is calculated from bookings and maintenance blocks for any period (FR8).|
|Vehicle availability is not stored|It is derived from bookings and maintenance blocks for the requested dates (FR2, FR7).|
|rental\_amount is stored on booking|It is a snapshot of the rate at booking time, so later changes to daily\_rate do not change past bookings.|
|Vehicle model and daily rate are in vehicle|If rates are later set per category, a vehicle\_category table is added in the same way faculty was extracted from course.|

## 3NF check

Every non-key column in each table depends only on that table's primary key. Branch details are not repeated in vehicle or staff, customer details are not repeated in booking, and licence details are not repeated in customer.

