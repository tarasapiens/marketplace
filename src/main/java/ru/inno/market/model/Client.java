package ru.inno.market.model;

import java.util.Objects;

public class Client {
    private int id;
    private String nickname;

    public Client(int id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public int getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client client)) return false;
        return getId() == client.getId() && Objects.equals(getNickname(), client.getNickname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getNickname());
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", nickname='" + nickname + '\'' +
                '}';
    }
}
