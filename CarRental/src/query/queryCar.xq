<cars>      

    {
    let $carR := doc("../../output/output.xml")/carRental/cars/car
    let $countCar :=  count($carR)
    return <amount_cars> {$countCar}</amount_cars>
    
    }{

    for $car in doc("../../output/output.xml")/carRental/cars/car

    return 
            <car>               
                {$car/id} 
                {$car/numberPlate} 
                {$car/pricePerDay} 
                {
                let $id := $car/id                    
                let $lease := doc("../../output/output.xml")/carRental/leases/lease
                let $countC := count($lease[car = $id])                    
                return <rented_times>{$countC}</rented_times>
                }                               
            </car>
    }

</cars>