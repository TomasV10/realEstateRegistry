# Real Estate Registry

# 1 Step

Run application (RealEstateRegistryApplication file)
H2 - in memory DB is used, no need additional setup, just run the application.
Database will be created automatically. You may check schema.sql and data.sql
files in resources folder.

Default port is changed to :8999. To reach the database use link:
http://localhost:8999/h2-console

## Swagger documentation

http://localhost:8999/swagger-ui.html

# 2 Step

Set up login to DB.
Driver Class: org.h2.Driver
JDBC URL: jdbc:h2:mem:property
User Name: sa
Password: 

# 3 Get all properties

To test REST APIs, I was using Postman.

Enter request URL to Postman: http://localhost:8999/api/properties (GET)

# 4 Add new property

Enter request URL: http://localhost:8999/api/properties/ (POST)
In Postman, "Body" section, choose "raw" and set type to JSON
Body example: 
{
"ownerId": 3,
"address": "Jonava, Draugystės g. 13",
"areaSize": 179.72,
"marketValue": 55000,
"propertyType": "HOUSE"
}

**NOTE** make sure, that the owner with your given ID exist.

If you want to add new property for new owner, just create new owner first.
Enter request URL: http://localhost:8999/api/owners/ (POST)
In Postman, "Body" section, choose "raw" and set type to JSON
Body example:
{
"fullName": "Steve McKenzie"
}

# 5 Update property

Enter request URL with desired property's ID you want to update:
http://localhost:8999/api/properties/2 (PUT)
Body structures is same as described in the fourth step.

# 6 Delete property

Enter request URL with desired property's ID, you want to delete:
http://localhost:8999/api/properties/2 (DELETE)

# 7 Get all owners

Enter request URL: http://localhost:8999/api/owners/ (GET)

# 8 Get all owner's properties

Enter request URL with desired owner's ID: 
http://localhost:8999/api/owners/2/properties (GET)

# 9 Calculate taxes

Enter request URL with desired owner's ID and *date("when"):
http://localhost:8999/api/owners/2/taxes?when=2021-02-12
If no date is specified, then "when" is current day.

*date is optional 

# 10 Get tax rates

Enter request URL: http://localhost:8999/api/taxRates (GET)

# 11 Get rate

Enter request URL with desired ID: http://localhost:8999/api/taxRates/2 (GET)

# 12 Add new Tax Rate

Enter request URL:
http://localhost:8999/api/taxRates/ (POST)
In Postman, "Body" section, choose "raw" and set type to JSON
Body example:
{
"validFrom": "2021-03-11",
"validTo": null,
"rate": 0.04,
"propertyType": "RESTAURANT"
}
**NOTE** propertyType can only be as specified in the PropertyType.java file.
(path to file: com/practise/realEstateRegistry/property/PropertyType.java)

# 13 Update tax rate

Enter request URL with desired rate's ID you want to update:
http://localhost:8999/api/taxRates/5 (PUT)
Body structures is same as described in the twelfth step.













