# PrescriptionHolder_Api
## Spring JPA - for creating tables in sql and queries over dataBase extending Repository or query Annotations.
## Spring Security - Configuring Spring Security Configuration for authenticating and authorizing users.
## Json Web Token - For session and authenticating and authorizing users.
 ## Spring Filters -  For redirecting users to check validity of tokens.
## Spring MongoDb - for data storage.

## PrescriptionHolder to add , update , create ,delete and user management. Using Spring boot with spring Security , Mongo Db , JWT , and custom filters for security.

# Register a user , by passing body with required information and return a JWT token or error if duplicate exists.
![register](https://user-images.githubusercontent.com/22851920/87258006-0f475080-c46e-11ea-8b4e-dc3adde877ff.PNG)

# Login a user , by passing requires information in body and return a JWT Token or error if credentials are wrong
![login](https://user-images.githubusercontent.com/22851920/87258005-0f475080-c46e-11ea-9e77-06e32ade478b.PNG)

# Update password
![updatePAssword](https://user-images.githubusercontent.com/22851920/87258002-0f475080-c46e-11ea-8250-5ede266d5ec5.PNG)

# insert a new prescription with required details and "authToken" in header section
![insertNew](https://user-images.githubusercontent.com/22851920/87258004-0f475080-c46e-11ea-870f-b2dc8a322c30.PNG)

# get all prescriptions for a user , by passing "authToken" in header section
![getAll](https://user-images.githubusercontent.com/22851920/87258003-0f475080-c46e-11ea-9b5e-7a5caf1e65e7.PNG)


#update a particular presctiption , as each prescription has unique name , by passing a individual prescription and "authToken" in header section
![update](https://user-images.githubusercontent.com/22851920/87258001-0eaeba00-c46e-11ea-8b78-79b5556eba1c.PNG)

#remove a particular presctiption , as each prescription has unique name , by passing a  prescription name "pName" and "authToken" in header section
![remove](https://user-images.githubusercontent.com/22851920/87258000-0eaeba00-c46e-11ea-9d40-07e0b564ec31.PNG)
