# ChowChariot - Fast Meal Delivery to Your Doorstep

## High-Level Function Design

### Project Statement
ChowChariot is a food delivery platform that connects hungry users with a an array of local restaurants and enables rapid order delivery. Hungry customers can browse through a wide range of restaurants and their respective menus and place an order to be delivered to their home. Restaurants can recieve orders and view their aggregate order data/metrics. Drivers deliver the orders straight to the customer.


### Minimum Viable Product (MVP) Features
#### Users
- Log in and out
- Browse restaurants and menus
- Place orders

#### Restaurants
- Log in and out
- Update their menu
- View incoming orders
- View order history and money

#### Drivers
- Log in and out
- View current assignment
- View delivery details (address, name, phone #)
- View delivery history and money

### Noun-Verb Analysis

#### Nouns
- User
- Restaurant
- Order
- Menu
- Item
- Driver

### Verbs
- Log in/out
- Place Order
- View
- Update
- Browse

## Functional Spec
### Entities

#### User
- userID (PK)  
- firstName 
- lastName 
- email
- hash 
- phone 
- location 

#### Driver 
- driverID (PK)
- firstName 
- lastName 
- email 
- hash 
- phone 
- location 

#### Restaurants 
- restID (PK)
- name
- menuID (FK Menus)
- hash
- email
- cuisineType
- location
- phone 
- hours 

#### Menus 
- menuID (PK)
- restID (FK Restaurants)

#### Items 
- itemID (PK)
- menuID (FK Menus)
- itemName 
- category
- unitCost

#### Orders 
- orderID (PK)
- restID (FK Restaurants)
- userID (FK Users)
- driverID (FK Drivers)
- total 
- status 
- createdAt

#### OrderItem 
- orderID (FK Orders)
- itemID (FK Items)
- quantity int

### Additional Details / Constraints

### Features for Future Versions
- Give drivers ability to accept or decline order
- Give restaurants ability to acc3ept or decine order
- live order status updates
- Communication between driver and customer

