const API_BASE = "http://localhost:8090/api";

async function apiGet(url) {
    const resp = await fetch(API_BASE + url);
    return resp.json();
}

async function apiPost(url, body) {
    const resp = await fetch(API_BASE + url, {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(body)
    });
    return resp.json();
}
