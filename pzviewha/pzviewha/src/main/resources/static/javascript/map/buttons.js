var rotation = 0

//Вращает карту
function Rotate() {
    rotation = rotation + 90;
    viewer.viewport.setRotation(rotation);
}

//Фокусируемся на определенном объекте на изображении
function Focus(x, y) {
    //Если не получили координаты - просто центрируем, иначе приближаем к цели
    if(x === undefined || y === undefined ){
        viewer.viewport.panTo(new OpenSeadragon.Point(.5, .5));
        viewer.viewport.zoomTo(1);
    } else {

        console.log("Get x:" + x + ' y:' + y)
        var point = viewer.viewport.imageToViewportCoordinates(x, y);

        console.log("Get new x: " + point.x + " y: " + point.y);
        viewer.viewport.zoomTo(2);
        viewer.viewport.panTo(point, false);
        //Такой плавный "переход"
        setTimeout(() => {
            viewer.viewport.zoomTo(30);
        }, 850);

    }
}


//Находит и отображает/прячет дома игроков
function ToogleSafehouses() {
    for (var elementByClassNameElement of document.body.getElementsByClassName("safehouse")) {
        console.log(elementByClassNameElement.style.display);
        if (elementByClassNameElement.style.display === "block") {
            elementByClassNameElement.style.display = "none";
            console.log('Прячем');
        } else {
            elementByClassNameElement.style.display = "block";
            console.log('Показываем');
        }
    }
}

//Находит и отображает/прячет игроков
function TooglePlayers() {
    for (var elementByClassNameElement of document.body.getElementsByClassName("player")) {
        console.log(elementByClassNameElement.style.display);
        if (elementByClassNameElement.style.display === "block") {
            elementByClassNameElement.style.display = "none";
            console.log('Прячем');
        } else {
            elementByClassNameElement.style.display = "block";
            console.log('Показываем');
        }
    }
}