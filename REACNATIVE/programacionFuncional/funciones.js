/*
ejecutarSumar= function(){
   let v1= recuperarEntero("txtValor1");
    let v2= recuperarEntero("txtValor2");

    let total=sumar(v1,v2)

    
     console.log("la suma es: " +total)
}

ejecutarResta=()=>{
    let v1 = recuperarEntero("txtValor1");
    let v2 = recuperarEntero("txtValor2");
    let resultado=restar(v1,v2);

    console.log("la resta es: "+resultado)


}


/* programacion funcional */

ejecutarOperacion=(operar)=>{
    let v1= recuperarEntero("txtValor1");
    let v2= recuperarEntero("txtValor2");

    let resultado;
    resultado= operar(v1,v2);
    console.log(resultado)

}


sumar=(n1,n2)=>{
    let resultado= n1 + n2;
    return resultado;

}


restar=(n1,n2)=>{
    let resultadoResta= n1 - n2;
    return resultadoResta;

}

ejecutar=()=>{

}

/* programacion funcional   una funcion puede recibir como parametro otra funcion */

ejecutar=(fn)=>{
    console.log("HOLA");
    fn();
}


imprimir=()=>{
    console.log("imprimiendo");
}
saludar=()=>{
    alert("estor aprendiendo programacion funcional")
}

testEjecutar=()=>{
    ejecutar(imprimir);
    ejecutar(saludar);
    ejecutar(()=>{
        alert("soy una funcion sin nombre");
    }

    );
}

