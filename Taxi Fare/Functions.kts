// Gojek Fare Functions
fun gojeck(x: Float): Float{// distance in km
    var price = 0
    if (x >= 4.72){
        price = (0.65*x)+2.7
    } else if (x < 4.72 && x > 0) {
        price = 6
    }
    return price
}

// ComfortDelgro Fare Functions
fun ComfortDelgro(dist: Float): Float{
    var distLeft: Float = dist
    var fare: Float = 0
    if(distLeft <= 0){
        fare = 0
    }
    if(distLeft<=1){
        fare = 4.10
        return fare
    }
    // more than 1 km
    distLeft -= 1
    fare = 4.10

    // dist - distLeft represents distance covered
    while(distLeft && (dist-distLeft) <= 10){
        fare += 0.24
        distLeft -= 400
    }

    //change in charge after 10km
    while(distLeft){
        fare += 0.24
        distLeft -= 350
    }

    return fare
}

//Grab Fare Function (never include cost per min because hard to calculate total duration of ride
fun grab(y: Float): Float{// distance in km
    var price = 0
    if (y >= 4.72){
        price = (0.50 * y) + 2.5
    } else if (x < 4.72 && x > 0) {
        price = 6
    }
    return price
}

//Uber Fare Function -- uber never share their prices