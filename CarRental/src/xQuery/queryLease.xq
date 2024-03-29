<leases>
    {
    let $leaseL := doc("output/output.xml")/carRental/leases/lease
    let $countLease :=  count($leaseL)
    return <amount_leases> {$countLease}</amount_leases>
    
    }{
    let $car := doc("output/output.xml")/carRental/cars/car
    let $customer := doc("output/output.xml")/carRental/customers/customer
    for $lease in doc("output/output.xml")/carRental/leases/lease
    return 
            <lease>
                <id_lease>{$lease/id/text()}</id_lease> 
                <date_from>{$lease/from/text()}</date_from> 
                <date_to>{$lease/to/text()}</date_to> 
                <date_real_return>{$lease/realReturn/text()}</date_real_return> 
                <car>
                <id_car>{$lease/car/text()}</id_car>
                {$car[id = $lease/car]/numberPlate}
                {$car[id = $lease/car]/pricePerDay}
                </car>
                <customer>
                <id_customer>{$lease/customer/text()}</id_customer>
                {$customer[id = $lease/customer]/firstName}
                {$customer[id = $lease/customer]/surname}
                {$customer[id = $lease/customer]/phone}
                </customer>
            </lease>
}    
</leases>