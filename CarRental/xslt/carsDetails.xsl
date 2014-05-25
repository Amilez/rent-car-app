<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : carsDetails.xsl
    Created on : May 23, 2014, 8:44 AM
    Author     : honza
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="cars">
        <html>
            <head>
                <title>Car details</title>
                <meta http-equiv="Content-Style-Type" content="text/css"/>
    <link rel="stylesheet" href="style.css" type="text/css" media="screen"/>
            </head>
            <body>
                <h1>cars</h1>
                
                <xsl:for-each select="car">
                    <div class="element">
                <h2>
                <xsl:attribute name="id">
                <xsl:value-of select="id"/></xsl:attribute>
                
                    <xsl:value-of select="numberPlate"/>
                </h2>
                <br/>
                <strong> price per day:</strong>
                <xsl:text> </xsl:text> 
                <xsl:value-of select="pricePerDay"/>
                <br/>
                <strong>rented times:</strong> 
                <xsl:text> </xsl:text>
                <xsl:value-of select="rented_times"/> 
                <xsl:if test="rented_times > 0">
                <xsl:text> </xsl:text>- in lease(s)
                <xsl:for-each select="leases/id">
                    <a>
                        <xsl:attribute name="href">leasesDetails.html#<xsl:value-of select="."/></xsl:attribute>
                        <xsl:value-of select="."/>
                        
                    </a>
                    <xsl:if test="position() != last()" >
                        ,
                        </xsl:if>
                </xsl:for-each>
                </xsl:if>
                <br/>
                
                <strong>rented by:</strong>
                <ul>
               <xsl:for-each select="customers/customer">
                <li>
                <xsl:value-of select="firstName"/>
                <xsl:text> </xsl:text>
                    <xsl:value-of select="surname"/>
                    <xsl:text> </xsl:text>
                    (<xsl:value-of select="rented_times"/>x)
                    
                </li>
                
                </xsl:for-each>
                </ul>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
