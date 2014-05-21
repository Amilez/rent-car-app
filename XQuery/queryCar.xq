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
            </car>
    }

</cars>