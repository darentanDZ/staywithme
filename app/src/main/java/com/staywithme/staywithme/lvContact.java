package com.staywithme.staywithme;

/**
 * Created by daren on 2/11/2016.
 */

public class lvContact {


        private String Name="";
        private String PhoneNumber="";

        /*********** Set Methods ******************/

        public void setName(String Name)
        {
            this.Name = Name;
        }

        public void setPhoneNumber(String PhoneNumber)
        {
            this.PhoneNumber = PhoneNumber;
        }

        /*********** Get Methods ****************/

        public String getName()
        {
            return this.Name;
        }

        public String getPhoneNumber()
        {
            return this.PhoneNumber;
        }

}
