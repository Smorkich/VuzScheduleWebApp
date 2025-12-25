async function loadClassrooms() {
    const data = await apiGet("/classrooms");
    const tbody = document.getElementById("classroomTableBody");
    tbody.innerHTML = "";

    data.forEach(c => {
        tbody.innerHTML += `
            <tr>
                <td>${c.id}</td>
                <td>${c.name}</td>
            </tr>`;
    });
}

document.getElementById("classroomForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("name").value;

    await apiPost("/classrooms", { name });
    document.getElementById("name").value = "";

    await loadClassrooms();
});

loadClassrooms();
