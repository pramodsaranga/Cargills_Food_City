package view.tm;

public class CustomerTm {
    private String customerId;
    private String customerTitle;
    private String customerName;
    private String Address;
    private String City;
    private String province;
    private String postalCode;

    public CustomerTm() {
    }

    public CustomerTm(String customerId, String customerTitle, String customerName, String address, String city, String province, String postalCode) {
        this.setCustomerId(customerId);
        this.setCustomerTitle(customerTitle);
        this.setCustomerName(customerName);
        this.setAddress(address);
        this.setCity(city);
        this.setProvince(province);
        this.setPostalCode(postalCode);
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerTitle() {
        return customerTitle;
    }

    public void setCustomerTitle(String customerTitle) {
        this.customerTitle = customerTitle;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return "CustomerTm{" +
                "customerId='" + customerId + '\'' +
                ", customerTitle='" + customerTitle + '\'' +
                ", customerName='" + customerName + '\'' +
                ", Address='" + Address + '\'' +
                ", City='" + City + '\'' +
                ", province='" + province + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}
