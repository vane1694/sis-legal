/**
 * Tipo_Cambio.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.bbva.general.entities;

public class Tipo_Cambio  implements java.io.Serializable {
    private java.lang.String tcMin;

    private java.lang.String tcMax;

    private java.lang.String tcPromedio;

    private java.lang.String divisa;

    private java.lang.String tipo;

    public Tipo_Cambio() {
    }

    public Tipo_Cambio(
           java.lang.String tcMin,
           java.lang.String tcMax,
           java.lang.String tcPromedio,
           java.lang.String divisa,
           java.lang.String tipo) {
           this.tcMin = tcMin;
           this.tcMax = tcMax;
           this.tcPromedio = tcPromedio;
           this.divisa = divisa;
           this.tipo = tipo;
    }


    /**
     * Gets the tcMin value for this Tipo_Cambio.
     * 
     * @return tcMin
     */
    public java.lang.String getTcMin() {
        return tcMin;
    }


    /**
     * Sets the tcMin value for this Tipo_Cambio.
     * 
     * @param tcMin
     */
    public void setTcMin(java.lang.String tcMin) {
        this.tcMin = tcMin;
    }


    /**
     * Gets the tcMax value for this Tipo_Cambio.
     * 
     * @return tcMax
     */
    public java.lang.String getTcMax() {
        return tcMax;
    }


    /**
     * Sets the tcMax value for this Tipo_Cambio.
     * 
     * @param tcMax
     */
    public void setTcMax(java.lang.String tcMax) {
        this.tcMax = tcMax;
    }


    /**
     * Gets the tcPromedio value for this Tipo_Cambio.
     * 
     * @return tcPromedio
     */
    public java.lang.String getTcPromedio() {
        return tcPromedio;
    }


    /**
     * Sets the tcPromedio value for this Tipo_Cambio.
     * 
     * @param tcPromedio
     */
    public void setTcPromedio(java.lang.String tcPromedio) {
        this.tcPromedio = tcPromedio;
    }


    /**
     * Gets the divisa value for this Tipo_Cambio.
     * 
     * @return divisa
     */
    public java.lang.String getDivisa() {
        return divisa;
    }


    /**
     * Sets the divisa value for this Tipo_Cambio.
     * 
     * @param divisa
     */
    public void setDivisa(java.lang.String divisa) {
        this.divisa = divisa;
    }


    /**
     * Gets the tipo value for this Tipo_Cambio.
     * 
     * @return tipo
     */
    public java.lang.String getTipo() {
        return tipo;
    }


    /**
     * Sets the tipo value for this Tipo_Cambio.
     * 
     * @param tipo
     */
    public void setTipo(java.lang.String tipo) {
        this.tipo = tipo;
    }

    private java.lang.Object __equalsCalc = null;
    public synchronized boolean equals(java.lang.Object obj) {
        if (!(obj instanceof Tipo_Cambio)) return false;
        Tipo_Cambio other = (Tipo_Cambio) obj;
        if (obj == null) return false;
        if (this == obj) return true;
        if (__equalsCalc != null) {
            return (__equalsCalc == obj);
        }
        __equalsCalc = obj;
        boolean _equals;
        _equals = true && 
            ((this.tcMin==null && other.getTcMin()==null) || 
             (this.tcMin!=null &&
              this.tcMin.equals(other.getTcMin()))) &&
            ((this.tcMax==null && other.getTcMax()==null) || 
             (this.tcMax!=null &&
              this.tcMax.equals(other.getTcMax()))) &&
            ((this.tcPromedio==null && other.getTcPromedio()==null) || 
             (this.tcPromedio!=null &&
              this.tcPromedio.equals(other.getTcPromedio()))) &&
            ((this.divisa==null && other.getDivisa()==null) || 
             (this.divisa!=null &&
              this.divisa.equals(other.getDivisa()))) &&
            ((this.tipo==null && other.getTipo()==null) || 
             (this.tipo!=null &&
              this.tipo.equals(other.getTipo())));
        __equalsCalc = null;
        return _equals;
    }

    private boolean __hashCodeCalc = false;
    public synchronized int hashCode() {
        if (__hashCodeCalc) {
            return 0;
        }
        __hashCodeCalc = true;
        int _hashCode = 1;
        if (getTcMin() != null) {
            _hashCode += getTcMin().hashCode();
        }
        if (getTcMax() != null) {
            _hashCode += getTcMax().hashCode();
        }
        if (getTcPromedio() != null) {
            _hashCode += getTcPromedio().hashCode();
        }
        if (getDivisa() != null) {
            _hashCode += getDivisa().hashCode();
        }
        if (getTipo() != null) {
            _hashCode += getTipo().hashCode();
        }
        __hashCodeCalc = false;
        return _hashCode;
    }

    // Type metadata
    private static org.apache.axis.description.TypeDesc typeDesc =
        new org.apache.axis.description.TypeDesc(Tipo_Cambio.class, true);

    static {
        typeDesc.setXmlType(new javax.xml.namespace.QName("http://entities.general.bbva.com", "Tipo_Cambio"));
        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcMin");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tcMin"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcMax");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tcMax"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tcPromedio");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tcPromedio"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("divisa");
        elemField.setXmlName(new javax.xml.namespace.QName("", "divisa"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
        elemField = new org.apache.axis.description.ElementDesc();
        elemField.setFieldName("tipo");
        elemField.setXmlName(new javax.xml.namespace.QName("", "tipo"));
        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
        elemField.setNillable(true);
        typeDesc.addFieldDesc(elemField);
    }

    /**
     * Return type metadata object
     */
    public static org.apache.axis.description.TypeDesc getTypeDesc() {
        return typeDesc;
    }

    /**
     * Get Custom Serializer
     */
    public static org.apache.axis.encoding.Serializer getSerializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanSerializer(
            _javaType, _xmlType, typeDesc);
    }

    /**
     * Get Custom Deserializer
     */
    public static org.apache.axis.encoding.Deserializer getDeserializer(
           java.lang.String mechType, 
           java.lang.Class _javaType,  
           javax.xml.namespace.QName _xmlType) {
        return 
          new  org.apache.axis.encoding.ser.BeanDeserializer(
            _javaType, _xmlType, typeDesc);
    }

}
