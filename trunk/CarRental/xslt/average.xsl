<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : average.xsl
    Created on : May 23, 2014, 8:23 AM
    Author     : honza
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="statistics">
        <html>
            <head>
                <title>Car Rental</title>
                <meta http-equiv="Content-Style-Type" content="text/css"/>
                <link rel="stylesheet" href="style.css" type="text/css" media="screen"/>
            </head>
            <body>
                <h1>Car Rental</h1>
                <ul>
                    <li>
                        <a href="cars.html" >cars</a>
                    </li>
                    <li>
                        <a href="customers.html" >customers</a>
                    </li>
                    <li>
                        <a href="leases.html" >leases</a>
                    </li>
                </ul>
                
                
               
                
                <h2>Statististics</h2>
                <table cellpadding="3">
                    <tr>
                        <td>       
                            <strong>amount cars:</strong> 
                        </td>
                        <td>
                            <xsl:value-of select="amount_cars" /> 
                        </td>
                    </tr>
                
                    <tr>
                        <td>
                            <strong>amount customers:</strong>
                        </td>
                        <td>
                            <xsl:value-of select="amount_customers" /> 
                        </td>
                    </tr>
                   
                    <tr>
                        <td>
                            <strong>average leases per car:</strong>
                        </td>
                        <td>
                            <xsl:value-of select="average_leases_per_car" /> 
                        </td>
                    </tr>
                        <tr>
                            <td>
                
                                <strong>average leases per customer:</strong>
                            </td>
                            <td>
                                <xsl:value-of select="average_leases_per_customer" />
                            </td>
                        </tr>
                
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
