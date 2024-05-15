ejecutarSumar= function(){
   let v1= recuperarEntero("txtValor1");
    let v2= recuperarEntero("txtValor2");

    let total=sumar(v1,v2)

    
     console.log("la suma es: " +total)
}

sumar=(n1,n2)=>{
    let resultado= n1 + n2;
    return resultado;

}


ejecutarResta=()=>{
    let v1 = recuperarFloat("txtValor1");
    let v2 = recuperarFloat("txtValor2");
    let resultado=restar(v1,v2);

    console.log("la resta es: "+resultado)


}


restar=(n1,n2)=>{
    let resultadoResta= n1 - n2;
    return resultadoResta;

}