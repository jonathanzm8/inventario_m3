recuperarTexto = function(idCompomente){
    let compomente= document.getElementById(idCompomente);
    let valorCompomente= compomente.value;
    return valorCompomente;

}

// arrows funtions 

recuperarTexto=(idCompomente)=>{
    let compomente= document.getElementById(idCompomente);
    let valorCompomente= compomente.value;
    return valorCompomente;

}

recuperarEntero=(idCompomente)=>{
    let valorTexto= recuperarTexto(idCompomente);
    let valorEntero= parseInt(valorTexto);
    return valorEntero;

}

recuperarFloat=(idCompomente)=>{
    let valorTexto= recuperarTexto(idCompomente);
    let valorFloat= parseFloat(valorTexto);
    return valorFloat;

}