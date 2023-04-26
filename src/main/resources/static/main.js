var content = document.getElementById("content");
let controller = null;
// Closure
const requestPromise = (count, reqController) => {
    const URL = "https://jsonplaceholder.typicode.com/todos/"
    return new Promise(async (resolve, reject) => {
        content.innerHTML = ""
        // Cancelacion de la promesa
        reqController.signal.addEventListener("abort", () => reject("Se cancelaron las solicitudes"))

        for (let i = 1; i <= count; i++) {
            if (reqController.signal.aborted) return;

            const response = await fetch(URL + i, {
                signal: reqController.signal
            })
            const result = await response.json()
            content.innerHTML = result.title + content.innerHTML
        }
        resolve()
    })
}

const start = () => {
    controller = new AbortController()
    const request = requestPromise(100, controller)

    request
        .then(() => {
            content.innerHTML = `
                <span>Se finalizaron las solicitudes</span>
                </br>
                ${content.innerHTML}
            `}
        ).catch(e => content.innerHTML = `<span style="color:red">Request Cancelado</span>`)
}

// Abort controller te permite cancelar solicitudes http
const cancel = () => {
    controller.abort()
}