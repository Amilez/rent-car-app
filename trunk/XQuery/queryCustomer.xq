<customers>
    {
    let $customerC := doc("input.xml")/carRental/customers/customer
    let $countCus :=  count($customerC)
    return <amount_customers> {$countCus}</amount_customers>
    
    }{
    for $customer in doc("input.xml")/carRental/customers/customer
    return 
            <customer>
                {$customer/firstName}
                {$customer/surname}
                {$customer/phone}
                {
                let $id := $customer/id                    
                let $lease := doc("input.xml")/carRental/leases/lease
                let $countC := count($lease[customer = $id])                    
                return <rented_times>{$countC}</rented_times>
                } 
            </customer>
}    
</customers>