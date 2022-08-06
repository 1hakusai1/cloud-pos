package jp.co.smartware.domain.product;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class JANCode implements Serializable {

    @Column(name = "jan_code")
    private long value;

    protected JANCode() {
    }

    public JANCode(long value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (value ^ (value >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        JANCode other = (JANCode) obj;
        if (value != other.value)
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "JANCode [value=" + value + "]";
    }

    long getValue() {
        return value;
    }
}
