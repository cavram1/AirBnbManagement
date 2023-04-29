# Projekt "AirBnb.com"

## Aufgabenstellung
Es ist die Managementsoftware für einen Verleiher von Wohnungen umszusetzten.


> **Beispiel:** Frau Mayer ist aufgrund einer Dienstreise der Suche nach einer provisorischen wohnung.
> Er benötigt sie ab dem 26.01.2023 bis zum 02.03.2023.

Die entstehenden Kosten setzen sich aus einzelnen Tagessätzen und Fixkosten (versicherung bei bestimmten schäden) zusammen. 
So gibt es folgende Regeln:
* Die Tagessätze sind je Airbnb variabel und getrennt zu speichern
* Die Versicherung beträgt 30€.


> **Beispiel:** Frau Mayer verlässt am 02.03.2023 die Wohnung. Der Tagessatz für die Wohnung ist mit 15€ festgelegt. 
> In Summe ergibt das also:
> 3 Tagessätze * 15,50€ pro Tag + Versicherung

## Umsetzung
Erstellen Sie für den obenstehenden Sachverhalt eine SpringWeb - Anwendung. Erstellen Sie für die Entitäten jeweils eine airBnb - Klasse (z.B. Airbnb, Landlord, ...) in denen Sie die Eigenschaften und die Beziehungen der einzelnen Klassen zueinander abbilden. Diese airBnb - Klassen sollen über die entsprechenden Repositories und der JPA mit der Datenbank kommunizieren.

Erstellen Sie in Ihrer Anwendung Endpunkte zum Verwalten der Kunden (neu anlegen / bearbeiten / löschen), zum Verwalten der AirBnb's (neu anlegen / bearbeiten / löschen), und zum Buchen eines AirBnb's (entleihen / zurückbringen).

Beim Entleihen eines AirBnbs muss zuerst der Zeitraum übermittelt werden. Anschließend werden alle AirBnbs, die im gewählten Zeitraum noch verfügbar sind, geliefert. Nun können die Ergebnisse wiederum nach Location gefiltert werden. Anschließend wird der Kunde eingetragen und die Entleihung gespeichert.

Bei der Rückgabe eines AirBnbs muss zuerst der Kunde aus einer Liste gewählt werden. Anschließend wird der Airbnb der derzeit für den Kunden noch "offen" ist, angezeigt. 
Befüllen Sie Ihre Anwendung mit sinnvollen Testdaten (Kunden, Marken, Computerhardware, Entleihungen, ...). Gerne können Sie bei Unklarheiten eigene Annahmen machen.

Erstellen sie einen Docker Container für diese Anwendung

## Required classes
### class `airbnb`
 * address
 * airbnb_number (format A_1a, A_1b, ...)
 * price_per_night
 * location
 * description

### class `landlord`
* email
* password
* token
* token_validation

### class `customer`
* email
* fullname
* payment_information
* phonenumber

### class `booking`
* customer_id
* airbnb_id
* booking_from
* booking_end

### class `rating`
* airbnb_id
* star_rating (1-5)
* additional_info


## REST - endpoints

### Registrierung
* `POST` - Request
* Endpunkt: `/register`
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt:
  `{"email": "ratp@htl-steyr.ac.at", "password": "test", "firstname": "Peter", "lastname": "Rathgeb"}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` zurückgebeben.

### Login
* `POST` - Request
* Endpunkt: `/login`
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt:
  `{"email": "ratp@htl-steyr.ac.at", "password": "test"}`
* Im Erfolgsfall wird der Statuscode `200` / der Accesstoken, der für 10 Minuten gültig ist, zurückgegeben.
* Bei Misserfolg wird der Statuscode `401` zurückgebeben.

### Kunden erzeugen
* `POST` - Request
* Endpunkt: `/customer/create`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt.
  `{"name":"Peter Rathgeb", "email": "ratp@htl-steyr.ac.at"}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### Kunden ändern
* `POST` - Request
* Endpunkt: `/customer/update`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt.
  `{"id": 1, "name":"Peter Rathgeb", "email": "peter@rathgeb.at"}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### Kunden löschen
* `DELETE` - Request
* Endpunkt: `/customer/delete/{id}`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### Kunden laden
* `GET` - Request
* Endpunkt: `/customer/load`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `[{"id": 1, "name": "Peter Rathgeb", "email": "ratp@htl-steyr.ac.at"}, {"id": 2, "name": "Max Mustermann", "email": "max@mustermann.at"}, ...]`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### Kunde laden
* `GET` - Request
* Endpunkt: `/customer/load/{id}`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `{"id": 1, "name": "Peter Rathgeb", "email": "ratp@htl-steyr.ac.at"}`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb erzeugen
* `POST` - Request
* Endpunkt: `/airBnb/create`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt.
  `{"name":"Dell"}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb ändern
* `POST` - Request
* Endpunkt: `/airBnb/update`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt.
  `{"id": 1, "name":"Apple"}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb löschen
* `DELETE` - Request
* Endpunkt: `/airBnb/delete/{id}`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb laden
* `GET` - Request
* Endpunkt: `/airBnb/load`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `[{"id": 1, "name": "Dell"}, {"id": 2, "name": "Apple"}, ...]`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb erzeugen
* `POST` - Request
* Endpunkt: `/airBnb/create`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt.
  `{"name": "Lenovo Yoga 7 14arb", "price": 7.5, "airBnb": 1}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb ändern
* `POST` - Request
* Endpunkt: `/airBnb/update`
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt.
  `{"name": "Apple Probook M2", "price": 13.5, "airBnb": 1}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### airBnb löschen
* `DELETE` - Request
* Endpunkt: `/airBnb/delete/{id}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnbe laden
* `GET` - Request
* Endpunkt: `/airBnb/load`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `[{"id": 1, "name": "Apple Probook M2", "price": 13.5, "airBnb": 1}, {"id": 2, "name": "Lenovo Yoga 7 14 arb", "price": 7.5, "airBnb": 2}, ...]`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb laden
* `GET` - Request
* Endpunkt: `/airBnb/load/{id}`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `{"id": 1, "name": "Apple Probook M2", "price": 13.5, "airBnb": 1}`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb für Location laden
* `GET` - Request
* Endpunkt: `/airBnb/load/{airBnbId}`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `[{"id": 2, "name": "Lenovo Yoga 7 14 arb", "price": 7.5, "airBnb": 2}, {"id": 3, "name": "Lenovo Thinkpad", "price": 6.5, "airBnb": 2}, ...]`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### Freie AirBnbs im Zeitraum laden
* `GET` - Request
* Endpunkt: `/airBnb/period/{from}/{to}`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `[{"id": 2, "name": "Lenovo Yoga 7 14 arb", "price": 7.5, "airBnb": 2}, {"id": 3, "name": "Lenovo Thinkpad", "price": 6.5, "airBnb": 2}, ...]`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### AirBnb entleihen
* `POST` - Request
* Endpunkt: `/airBnb/rent`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Erfoderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt.
  `{"airBnbId": 1, "customerId": 1, "from": "2023-02-20", "to": "2023-02-25"}`
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben.
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### Offene Verleihungen für Kunden laden
* `GET` - Request
* Endpunkt: `/airBnb/customer`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `[{"id": 1, "airBnbId": "1", "customerId": 1, "from": "2023-02-20", "to": "2023-02-25"}, ...]`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.

### Verleihungen abschließen
* `POST` - Request
* Endpunkt: `/airBnb/done/{airBnbId}`
* Erforderliche Daten werden im Requst - Body als JSON - Objekt mitgeschickt.
  `{"loadings": 18}`
* Erforderlicher Token wird als Bearer - Token im Autorizationheader mitgeschickt.
* Im Erfolgsfall wird der Statuscode `200` zurückgegeben sowie die Daten im Requestbody zurückgegeben: `{"totalPrice": 392,40}`
* Bei Misserfolg wird der Statuscode `400` / `401` zurückgebeben.


## OpenAPI
Erstellen Sie für die fünf obenstehenden REST - Endpunkte eine Open-API - Doku, in der Sie die Parameter / den Requestbody sowie die Rückgabewerte und die Statuscodes beschreiben. Nähere Infos finden Sie unter https://www.baeldung.com/spring-rest-openapi-documentation

## Lombok
Erstellen Sie für Ihre Entity - Klassen die Getter- und Settermethoden über die Lombok - Bibliothek.

## AOP
Schreiben Sie mittels der in Spring integrierten AOP Funktinoalität einen Aspect, der alle Aufrufe der Controllermethoden in der Konsole mitloggt.
Ausgegeben werden soll:
* der Name der aufgerufenen Methode; davor
* die Werte der übergebenen Parameter (mittels `toString()`); davor
* der Rückgabewert (mittels `toString()`); danach
* Schreiben sie in einer text.file wann ein customer in die Wohnung eingecheckt ist und ausgecheckt ist

## JUNIT
Schreiben Sie sinnvolle Test für die REST - Schnittstellen. Testen sie dabei auf einer Testdatenbank, in Kombination mit Github Actions