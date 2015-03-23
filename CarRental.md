# Car Rental - Java application - Documentation #

This page is a documentation of a base of our whole application. It is here to show you how the principlas of our application.


This java application is used for administrating leases in Car Rental. The application is enabled to create, delete, update leases, cars and customers. It enables also to find one of this entities by various way (id, name, all ...). The application counts the profit from last month.

The application includes:<br>
<b>entities</b>: Car, Customer, Lease<br>
<b>managers</b>: Car Manager (Impl) , Customer Manager (Impl), Lease Manager (Impl)<br>
<br>
To <b>Use Case and Class Diagrams</b> click <a href='https://code.google.com/p/rent-car-app/wiki/CarRentalDiagrams'>here</a>


<h2>Car</h2>
attributes: <i>id (Long), pricePerDay (Big Decimal), numberPlate (String)</i>

<h2>Customer</h2>
attributes: <i>id (Long), phone (String), lastName (String), firstName (String)</i>

<h2>Lease</h2>
attributes: <i>fromDate (Date), id (Long), toDate (Date), car (Car), realReturn (Date), customer (Customer)</i>

<h2>Car Manager</h2>
methods: <i>createCar, updateCar, deleteCar, findCarById, findAllCars</i>
<ul>
<li>
<h4>Create Car</h4>
attributes: <i>Car</i>

return value: <i>void</i>

Adding the car into the manager.<br>
</li>
<li>
<h4>Update Car</h4>
attributes: <i>Car</i>

return value: <i>void</i>

Updating the car in the manager.<br>
</li>
<li>
<h4>Delete Car</h4>
attributes: <i>Car</i>

return value: <i>void</i>

Deleting the car from the manager.<br>
</li>
<li>
<h4>Find Car By Id</h4>
attributes: <i>Long</i>

return value: <i>Car</i>

Finding the car by the Id.<br>
</li>

<li>
<h4>Find All Cars</h4>
attributes: <i>void</i>

return value: <i>List (Car)</i>

Finding all cars.<br>
</li>

</ul>

<h2>Customer Manager</h2>
methods: <i>createCustomer, updateCustomer, deleteCustomer, findCustomerById, findAllCustomers, findCustomerByName</i>
<ul>
<li>
<h4>Create Customer</h4>
attributes: <i>Customer</i>

return value: <i>void</i>

Adding the customer into the manager.</li>

<li>
<h4>Update Customer</h4>
attributes: <i>Customer</i>

return value: <i>void</i>

Updating the customer in the manager.</li>

<li>
<h4>Delete Customer</h4>
attributes: <i>Customer</i>

return value: <i>void</i>

Deleting the customer from the manager.</li>

<li>
<h4>Find Customer By Id</h4>
attributes: <i>Long</i>

return value: <i>Customer</i>

Finding the customer by the Id.</li>


<li>
<h4>Find All Customers</h4>
attributes: <i>void</i>

return value: <i>List (Customer)</i>

Finding all customers.</li>


<li>
<h4>Find Customer By Name</h4>
attributes: <i>Long</i>

return value: <i>Customer</i>

Finding the customer by the name.</li>
</ul>

<h2>Lease Manager</h2>
methods: <i>createLease, updateLease, unLeasedCars, findLeaseByCustomer, lastMonthProfit</i>
<ul>
<li>
<h4>Create Lease</h4>
attributes: <i>Lease</i>

return value: <i>void</i>

Adding the lease into the manager.<br>
</li>
<li>
<h4>Update Lease</h4>
attributes: <i>Lease</i>

return value: <i>void</i>

Updating the lease in the manager.<br>
</li>
<li>
<h4>Unleased Cars</h4>
attributes: <i>void</i>

return value: <i>List (Car)</i>

Finding cars which are actually free.<br>
</li>
<li>
<h4>Find Lease By Customer</h4>
attributes: <i>Customer</i>

return value: <i>List (Lease)</i>

Finding the lease by the customer.<br>
</li>

<li>
<h4>Last Month Profit</h4>
attributes: <i>void</i>

return value: <i>Big Decimal</i>

Getting a profit from last month leases.<br>
</li>

</ul>