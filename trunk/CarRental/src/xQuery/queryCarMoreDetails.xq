<cars>
    {
    let $carR := doc("output/output.xml")/carRental/cars/car
    let $countCar :=  count($carR)
    return <amount_cars> {$countCar}</amount_cars>
    
    }{
    for $car in doc("output/output.xml")/carRental/cars/car
    return 
            <car>
                {$car/id} 
                {$car/numberPlate} 
                {$car/pricePerDay} 
                {
                let $id := $car/id                    
                let $lease := doc("output/output.xml")/carRental/leases/lease
                let $countC := count($lease[car = $id])                    
                return <rented_times>{$countC}</rented_times>
                }               
                <leases>                    
                    
                    {
                    let $id := $car/id                    
                    for $lease in doc("output/output.xml")/carRental/leases/lease
                    where $lease/car = $id                    
                    return $lease/id
                    }                     
                </leases>
                <customers>
                    {
                        let $idD := $car/id 
                        let $lease := doc("output/output.xml")/carRental/leases/lease
                        for $customer in distinct-values($lease[car = $idD]/customer)
                        return <customer>
                                <id>{$customer}</id>
                                {doc("output/output.xml")/carRental/customers/customer[id=$customer]/firstName}
                                {doc("output/output.xml")/carRental/customers/customer[id=$customer]/surname} 
                                <rented_times>{                                                                                                                   
                                let $countD := count($lease[car = $idD and customer = $customer])                    
                                return $countD  

                                }</rented_times>
                        </customer>

                    }
                </customers>
            </car>
}    
</cars>