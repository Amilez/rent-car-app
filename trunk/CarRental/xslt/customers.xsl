<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : customers.xsl
    Created on : May 23, 2014, 9:31 AM
    Author     : honza
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="customers">
        <html>
            <head>
                <title>customers</title>
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
                            <strong>firstname</strong>
                        </td>
                        <td>
                            <strong>surname</strong>
                        </td>
                        <td>
                            <strong>phone</strong>
                        </td>
                        <td>
                            <strong>rented times</strong>
                        </td>
                    </tr>
                <xsl:for-each select="customer">
                    <tr>
                        <td><a>
                            <xsl:attribute name="href">customersDetails.html#<xsl:value-of select="id"/></xsl:attribute>
                            <xsl:value-of select="id"/>
                        </a>
                        </td>
                        <td><xsl:value-of select="firstName"/></td>
                        <td><xsl:value-of select="surname"/></td>
                        <td><xsl:value-of select="phone"/></td>
                        <td><xsl:value-of select="rented_times"/></td>
                   
                        
                    </tr> 
                </xsl:for-each>
                </table>
                <p class="note">for more details, click on the id</p>
                
                <xsl:if test="amount_customers">
                   <strong> amount customers: </strong><xsl:value-of select="amount_customers"/>
                </xsl:if>
                
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
