# drones
## Drones task

### Exercise Assumptions:
- If the drone `Serial` has more than `100` characters, the remaining characters will be truncated and the drone creation will be executed.
- For medication: 
  - If the `name` has characters other than letters, numbers, `-` or `_` the service will not create the medication and will inform to the user accordingly.
  - If the `code` has lowercase letters, the system will change them to Uppercase and continue with the execution.
  - If the `code` has characters other than letters, numbers or `_` the service will not create the medication and will inform to the user accordingly.
  - The image attribute will store a path for the image.
- The medication quantity will be always available  on the database (Inventory will be out of the scope of the exercise).
- It will be a table to register the order which has the id of the order, the id of the medication and the quantity of that medication for that order. 
- It will be a table to register which drone was assigned to which order and the status of the order.
- A drone will have only ONE active order in a specific moment.
