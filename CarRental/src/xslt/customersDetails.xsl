<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : customersDetails.xsl
    Created on : May 23, 2014, 9:45 AM
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
                <title>custoemrs details</title>
                <meta http-equiv="Content-Style-Type" content="text/css"/>
    <link rel="stylesheet" href="style.css" type="text/css" media="screen"/>
            </head>
            <body>
                 <h1 class="center">customers</h1>
                
                <xsl:for-each select="customer">
                     <div class="element">
                <h2>
                <xsl:attribute name="id">
                <xsl:value-of select="id"/></xsl:attribute>
                
                <xsl:value-of select="firstName"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="surname"/>
                </h2>
                <br/>
                <strong> phone:</strong> 
                <xsl:value-of select="phone"/>
                <br/>
                <strong>returned times:</strong> 
                <xsl:value-of select="rented_times"/>
                <br/>
                
                <strong>rented cars:</strong>
                <ul>
               <xsl:for-each select="cars/car">
                <li>
                <xsl:value-of select="numberPlate"/>
                <xsl:text> </xsl:text>
                    
                    <xsl:value-of select="rented_times"/>
                    
                </li>
                
                </xsl:for-each>
                </ul>
                     </div>
                </xsl:for-each>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
