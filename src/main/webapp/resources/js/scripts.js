function verificar(xhr, status, args , dlg, tbl) {
	if (args.validationFailed) {
		PF(dlg).jq.effect("shake", {
			times : 5
		}, 100);
	} else {
		PF(dlg).hide();
		PF(tbl).clearFilters();
	}
}
//INICIO FUNÇÃO DE MASCARA MAIUSCULA
function maiuscula(z){
     v = z.value.toUpperCase();
     z.value = v;
 }
//FIM DA FUNÇÃO MASCARA MAIUSCULA

function start() {
    PF('statusDialog').show();
}
 
function stop() {
    PF('statusDialog').hide();
}