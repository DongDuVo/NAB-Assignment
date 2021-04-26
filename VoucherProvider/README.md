# VoucherProvider

## Step to run application on local machine
- Checkout source code from github
- Install JDK-11, Maven
- Open terminal run cd to source code folder
- Run command: mvn clean package
- Then cd to target folder and run command: java -jar voucher-provider.jar

## Sample Requests
- curl --location --request GET 'http://localhost:9099/api/voucher' --header 'x-api-key: cd719eef-2cff-40ff-b966-f7fc9da065d1'

