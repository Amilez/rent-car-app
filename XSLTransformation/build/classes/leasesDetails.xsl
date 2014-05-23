<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : leasesDetails.xsl
    Created on : May 23, 2014, 10:11 AM
    Author     : honza
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="leases">
        <html>
            <head>
                <title>leases details</title>
                <meta http-equiv="Content-Style-Type" content="text/css"/>
    <link rel="stylesheet" href="style.css" type="text/css" media="screen"/>
            </head>
            <body>
                
                         <h1>leases</h1>
                
                <xsl:for-each select="lease">
                    <div class="element">
                <h2>
                <xsl:attribute name="id">
                <xsl:value-of select="id_lease"/></xsl:attribute>
                lease id: <xsl:text> </xsl:text>
                <xsl:value-of select="id_lease"/>
                </h2>
                <strong> from:</strong>
                <xsl:text> </xsl:text> 
                <xsl:value-of select="date_from"/>
                <br/>
                <strong>to:</strong> 
                <xsl:text> </xsl:text>
                <xsl:value-of select="date_to"/>
                <br/>
                <strong>real return:</strong> 
                <xsl:text> </xsl:text>
                <xsl:value-of select="date_real_return"/>
               
                <br/>
                
                <strong>car:</strong>
                <xsl:text> </xsl:text>
                <a>
                    <xsl:attribute name="href">carsDetails.html#<xsl:value-of select="car/id_car"/></xsl:attribute>
                    <xsl:value-of select="car/numberPlate"/>
                </a>
                <br/>
                <strong>customer:</strong>
                <xsl:text> </xsl:text>
                <a>
                    <xsl:attribute name="href">customersDetails.html#<xsl:value-of select="customer/id_customer"/></xsl:attribute>
                    <xsl:value-of select="customer/firstName"/>
                    <xsl:text> </xsl:text>
                    <xsl:value-of select="customer/surname"/>
                </a>
                    </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
