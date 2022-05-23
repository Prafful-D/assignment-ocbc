1. Create 2 new users using create users api
   - http://{{host:port}}/assignment/users
   - Keep user id to be used for other transactions
2. Add money to users using TopUp api
   - http://{{host:port}}/assignment/topUp
   - use userid in set one to do topUp
3. Do transfer 
   - http://{{host:port}}/assignment/transfer
   - required two userid for transfer and amount to transfer
4. Please refer postman collect for API details
5. Default host name and port is as "localhost:8080"