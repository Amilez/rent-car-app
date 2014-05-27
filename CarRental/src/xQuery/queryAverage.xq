<statistics>       
    {
        let $car := doc("output/output.xml")/carRental/cars/car
        let $countCar :=  count($car)
        return <amount_cars>{$countCar}</amount_cars>    
    }
    {
        let $customer := doc("output/output.xml")/carRental/customers/customer
        let $countCus :=  count($customer)
        return <amount_customers>{$countCus}</amount_customers>
    }
    {        
        let $lease := doc("output/output.xml")/carRental/leases/lease  
        let $countLea :=  count($lease)
        return <amount_leases>{$countLea}</amount_leases>
    }
    {        
        let $lease := doc("output/output.xml")/carRental/leases/lease  
        let $countLea :=  count($lease)
        let $car := doc("output/output.xml")/carRental/cars/car
        let $countCar :=  count($car)        
        return if ($countCar != 0)
        then <average_leases_per_car>{$countLea div $countCar}</average_leases_per_car>
        else <average_leases_per_car>0</average_leases_per_car>
    } 
   {        
        let $lease := doc("output/output.xml")/carRental/leases/lease  
        let $countLea :=  count($lease)
        let $customer := doc("output/output.xml")/carRental/customers/customer
        let $countCus :=  count($customer)       
        return if ($countCus != 0)
        then <average_leases_per_customer>{$countLea div $countCus}</average_leases_per_customer>
        else <average_leases_per_customer>0</average_leases_per_customer>
    }           

    

   
   
</statistics>