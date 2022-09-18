package guru.qa.domain;


public class SteamUser {

    private String UserName;
    private Integer Age;
    private String Country;
    private Boolean StatusOnline;
    private String[] GamesCollections;

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(Integer age) {
        Age = age;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public Boolean getStatusOnline() {
        return StatusOnline;
    }

    public void setStatusOnline(Boolean statusOnline) {
        StatusOnline = statusOnline;
    }

    public String[] getGamesCollections() {
        return GamesCollections;
    }

    public void setGamesCollections(String[] gamesCollections) {
        GamesCollections = gamesCollections;
    }


}
