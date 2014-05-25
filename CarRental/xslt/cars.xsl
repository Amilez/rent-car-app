<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : index.xsl
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
    <xsl:template match="cars">
        <html>
            <head>
                <title>cars</title>
                <meta http-equiv="Content-Style-Type" content="text/css"/>
    <link rel="stylesheet" href="style.css" type="text/css" media="screen"/>
            </head>
            <body>
                <table border="1px">
                    <tr>
                        <td>
                            <strong>id</strong>
                        </td>
                        <td>
                            <strong>number plate</strong>
                        </td>
                        <td>
                            <strong>price per day</strong>
                        </td>
                        <td>
                            <strong>rented times</strong>
                        </td>
                    </tr>
                <xsl:for-each select="car">
                    <tr>
                        <td><a>
                            <xsl:attribute name="href">carsDetails.html#<xsl:value-of select="id"/></xsl:attribute>
                            <xsl:value-of select="id"/>
                        </a>
                        </td>
                        <td><xsl:value-of select="numberPlate"/></td>
                        <td><xsl:value-of select="pricePerDay"/></td>
                        <td><xsl:value-of select="rented_times"/></td>
                   
                        
                    </tr> 
                </xsl:for-each>
                </table>
		<p class="note">for more details, click on the id</p>
                
                <xsl:if test="amount_cars">
                    <strong>amount cars:</strong> 
                    <xsl:value-of select="amount_cars"/>
                </xsl:if>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
