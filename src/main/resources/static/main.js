;

const apiUrl = 'http://localhost:8080/api'

const submitNewUrlForm = document.getElementById('submit-new-url-form')
const tableUrls = document.getElementById('table-urls');
let urlItems = []

window.addEventListener('load', async () => loadUrls())
document.getElementById('update-table-url').addEventListener('click', () => loadUrls())

const createShortUrl = async (e) => {
    document.getElementById('errors').innerHTML = ''
    const inputs = e.getElementsByTagName('input')
    const inputUrl = inputs[0]
    const type = inputs[1].checked
        ? inputs[1].value
        : inputs[2].value

    const requestBody = {
        location: inputUrl.value,
        type: type
    }

    const options = {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(requestBody)
    }

    await fetch(`${apiUrl}/link/generate`, options)
        .then(resp => {
            if (resp.status >= 200 && resp.status < 300) {
                return resp
            }
            throw new Error()
        })
        .then(resp => resp.json())
        .then(item => {
            appendItem(item)
            urlItems.push(item)
        })
        .then(() => inputUrl.value = '')
        .catch(e => handleError(e))
}

const loadUrls = async () => {
    await fetch(`${apiUrl}/link`)
        .then(resp => resp.json())
        .then(json => json.content)
        .then(items => urlItems = items)
        .then(() => updateTableUrls())
}

const updateTableUrls = () => {
    tableUrls.getElementsByTagName('tbody')[0].innerHTML = ''
    urlItems.map(item => appendItem(item))
}

const appendItem = (item) => {
    const tbody = tableUrls.getElementsByTagName('tbody')[0]
    const tr = document.createElement('tr')
    tr.innerHTML = `
                <td>${item.track}</td>
                <td><a href="/api/link/${item.track}" target="_blank">${item.track}</a></td>
                <td><a href="${item.location}" target="_blank">${item.location}</a></td>
                <td>${new Date(item.createdAt).toLocaleDateString('ru-RU')}</td>
                <td>${item.redirectCount}</td>
            `

    tbody.appendChild(tr)
}

const handleError = (error) => {
    document.getElementById('errors').innerHTML = `
        <div class="p-3 alert-danger">
            <p>Произошла ошибка</p>
        </div> 
    `
}