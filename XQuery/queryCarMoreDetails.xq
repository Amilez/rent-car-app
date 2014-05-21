<cars>
    {
    let $carR := doc("input.xml")/carRental/cars/car
    let $countCar :=  count($carR)
    return <amount_cars> {$countCar}</amount_cars>
    
    }{
    for $car in doc("input.xml")/carRental/cars/car
    return 
            <car>
                {$car/id} 
                {$car/numberPlate} 
                {
                let $id := $car/id                    
                let $lease := doc("input.xml")/carRental/leases/lease
                let $countC := count($lease[car = $id])                    
                return <rented_times>{$countC}</rented_times>
                }               
                <leases>                    
                    
                    {
                    let $id := $car/id                    
                    for $lease in doc("input.xml")/carRental/leases/lease
                    where $lease/car = $id                    
                    return $lease/id
                    }                     
                </leases>
                <customers>
                    {
                        let $idD := $car/id 
                        let $lease := doc("input.xml")/carRental/leases/lease
                        for $customer in distinct-values($lease[car = $idD]/customer)
                        return <customer>
                                <id>{$customer}</id>
                                {doc("input.xml")/carRental/customers/customer[id=$customer]/firstName}
                                {doc("input.xml")/carRental/customers/customer[id=$customer]/surname} 
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