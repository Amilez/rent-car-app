    




<statistics>       
    {
        let $car := doc("output/output.xml")/carRental/cars/car
        let $countCar :=  count($car)
        return <amount_cars> {$countCar}</amount_cars>    
    }
    {
        let $customer := doc("output/output.xml")/carRental/customers/customer
        let $countCus :=  count($customer)
        return <amount_customers> {$countCus}</amount_customers>
    }
    {        
        let $lease := doc("output/output.xml")/carRental/leases/lease  
        let $countLea :=  count($lease)
        return <amount_leases> {$countLea}</amount_leases>
    }
    {        
        let $lease := doc("output/output.xml")/carRental/leases/lease  
        let $countLea :=  count($lease)
        let $car := doc("output/output.xml")/carRental/cars/car
        let $countCar :=  count($car)
        let $div :=  $countLea div $countCar
        return <average_leases_per_car> {$div}</average_leases_per_car>
    } 
    {        
        let $lease := doc("output/output.xml")/carRental/leases/lease  
        let $countLea :=  count($lease)
        let $customer := doc("output/output.xml")/carRental/customers/customer
        let $countCus :=  count($customer)
        let $div :=  $countLea div $countCus
        return <average_leases_per_customer> {$div}</average_leases_per_customer>
    } 

    

   
   
</statistics>