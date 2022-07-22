package com.example.taskapp.Modal;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public  class Modal {

    public static class Datum {

        @SerializedName("firstname")
        @Expose
        private String firstname;
        @SerializedName("lastname")
        @Expose
        private String lastname;
        @SerializedName("gender")
        @Expose
        private String gender;
        @SerializedName("picture")
        @Expose
        private String picture;
        @SerializedName("job")
        @Expose
        private List<Job> job = null;
        @SerializedName("education")
        @Expose
        private List<Education> education = null;
        @SerializedName("age")
        @Expose
        private Integer age;

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getPicture() {
            return picture;
        }

        public void setPicture(String picture) {
            this.picture = picture;
        }

        public List<Job> getJob() {
            return job;
        }

        public void setJob(List<Job> job) {
            this.job = job;
        }

        public List<Education> getEducation() {
            return education;
        }

        public void setEducation(List<Education> education) {
            this.education = education;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

    }

    public static class Education {

        @SerializedName("degree")
        @Expose
        private String degree;
        @SerializedName("institution")
        @Expose
        private String institution;

        public String getDegree() {
            return degree;
        }

        public void setDegree(String degree) {
            this.degree = degree;
        }

        public String getInstitution() {
            return institution;
        }

        public void setInstitution(String institution) {
            this.institution = institution;
        }

    }

    public static class Data {

        @SerializedName("data")
        @Expose
        private List<Datum> data = null;

        public List<Datum> getData() {
            return data;
        }

        public void setData(List<Datum> data) {
            this.data = data;
        }

    }

    public static class Job {

        @SerializedName("role")
        @Expose
        private String role;
        @SerializedName("exp")
        @Expose
        private Integer exp;
        @SerializedName("organization")
        @Expose
        private String organization;

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public Integer getExp() {
            return exp;
        }

        public void setExp(Integer exp) {
            this.exp = exp;
        }

        public String getOrganization() {
            return organization;
        }

        public void setOrganization(String organization) {
            this.organization = organization;
        }

    }
}