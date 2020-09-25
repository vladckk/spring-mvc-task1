<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
   pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equip="Content-Type" content="text/html; charset=ISO-8859-1">
        <title>Add new film</title>
        <style>
            div.field {
                padding-bottom: 5px;
            }
            div.field label {
                padding-top: 3px;
                display: block;
                float: left;
                width: 70px;
                height: 20px;
            }
            div.field input {
                height: 20px;
                width: 200px;
            }
        </style>
    </head>
    <body>
        <form action="/add" method="Post">
            <div class="field">
            <label>Title: </label>
            <input type="text" name="title" autocomplete="off" autofocus required>
            </div><br>
            <div class="field">
            <label>Genre: </label>
            <input type="text" name="genres" autocomplete="off" placeholder="musical, comedy, etc " required>
            </div><br>
            <div class="field">
            <label>Year: </label>
            <input type="number" name="year" autocomplete="off" required>
            </div><br>
            <div class="field">
            <label>Actors: </label>
            <input type="text" name="actors" autocomplete="off" placeholder="Ryan Gosling, Ann Hathaway, etc" required>
            </div><br>
            <div class="field">
            <label>Director: </label>
            <input type="text" name="director" autocomplete="off" required>
            </div><br>
            <input type="submit" value="Add new film">
        </form>
    </body>
</html>