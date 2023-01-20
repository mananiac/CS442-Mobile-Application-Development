package com.example.civiladvocacy;


public class Official {private String address_of_the_official;
    private String position_of_the_official;

    private String part_of_the_official;

    private String email_of_the_official;

    private String website_of_the_official;
    private String contact_of_the_official;
    private String photoURL_of_the_official;
    private String name_of_the_official;
    private String [] array_of_social_media = new String [3];

    public Official (String po, String n, String pa, String ph, String em, String addr, String web, String photo, String [] social){
        array_of_social_media = social;
        setName_of_the_official(n);
        setAddress_of_the_official(addr);

        setEmail_of_the_official(em);

        setWebsite_of_the_official(web);
        setPart_of_the_official(pa);
        setPhotoURL_of_the_official(photo);
        setPosition_of_the_official(po);
        setContact_of_the_official(ph);

    }


    public String getPhotoURL_of_the_official() {
        return photoURL_of_the_official;
    }


    public String getName_of_the_official() {
        return name_of_the_official;
    }
    public String getContact_of_the_official() {
        return contact_of_the_official;
    }

    public String getAddress_of_the_official() {
        return address_of_the_official;
    }



    public String getEmail_of_the_official() {
        return email_of_the_official;
    }


    public String getWebsite_of_the_official() {
        return website_of_the_official;
    }

    public String getPosition_of_the_official() {
        return position_of_the_official;
    }
    public String [] getSocialLink(){
        return array_of_social_media;
    }
    public String getPart_of_the_official() {
        return part_of_the_official;
    }


    public void setEmail_of_the_official(String email_of_the_official) {
        this.email_of_the_official = email_of_the_official;
    }


    public void setAddress_of_the_official(String address_of_the_official) {
        this.address_of_the_official = address_of_the_official;
    }

    public void setPart_of_the_official(String part_of_the_official) {
        this.part_of_the_official = "(" + part_of_the_official + ")";
    }

    public void setContact_of_the_official(String contact_of_the_official) {
        this.contact_of_the_official = contact_of_the_official;
    }



    public void setPhotoURL_of_the_official(String photoURL_of_the_official) {
        this.photoURL_of_the_official = photoURL_of_the_official;
    }
    public void setPosition_of_the_official(String position_of_the_official) {
        this.position_of_the_official = position_of_the_official;
    }
    public void setName_of_the_official(String name_of_the_official) {
        this.name_of_the_official = name_of_the_official;
    }
    public void setWebsite_of_the_official(String website_of_the_official) {
        this.website_of_the_official = website_of_the_official;
    }


    @Override
    public String toString() {
        return "Official{" +
                "position='" + position_of_the_official + '\'' +
                ", name='" + name_of_the_official + '\'' +
                ", party='" + part_of_the_official + '\'' +
                '}';
    }
}
