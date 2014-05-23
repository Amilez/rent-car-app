<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : leases.xsl
    Created on : May 23, 2014, 10:02 AM
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
                <title>leases</title>
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
                            <strong>from</strong>
                        </td>
                        <td>
                            <strong>to</strong>
                        </td>
                        <td>
                            <strong>real return</strong>
                        </td>
                    </tr>
                <xsl:for-each select="lease">
                    <tr>
                        <td><a>
                            <xsl:attribute name="href">leasesDetails.html#<xsl:value-of select="id_lease"/></xsl:attribute>
                            <xsl:value-of select="id_lease"/>
                        </a>
                        </td>
                        <td><xsl:value-of select="date_from"/></td>
                        <td><xsl:value-of select="date_to"/></td>
                        <td><xsl:value-of select="date_real_return"/></td>
                   
                        
                    </tr> 
                </xsl:for-each>
                </table>
                
                <xsl:if test="amount_leases">
                    <strong>amount leases:</strong> 
                    <xsl:value-of select="amount_leases"/>
                </xsl:if>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
