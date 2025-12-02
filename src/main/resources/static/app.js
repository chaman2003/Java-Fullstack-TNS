/*
    Certificate flow (concise, inline):
    - Frontend posts/puts JSON to `/api/certificate` including `qualification`.
    - Controller forwards request to Service.
    - Service enforces gap-free ID assignment for create and validates on update.
    - Repository persists to PostgreSQL (uses findAllIds() for ID logic).
*/
let currentEntity = 'admin';
const apiUrl = 'http://localhost:8080/api';

// Entity field definitions matching database schema
const entityFields = {
    admin: [
        { name: 'name', label: 'Full Name', type: 'text', required: true },
        { name: 'password', label: 'Password', type: 'password', required: true }
    ],
    student: [
        { name: 'name', label: 'Full Name', type: 'text', required: true },
        { name: 'phoneNo', label: 'Phone Number', type: 'tel', required: true },
        { name: 'CollegeName', label: 'College Name', type: 'text', required: true }
    ],
    college: [
        { name: 'collegeName', label: 'College Name', type: 'text', required: true },
        { name: 'location', label: 'Location', type: 'text', required: true }
    ],
    placement: [
        { name: 'name', label: 'Company Name', type: 'text', required: true },
        { name: 'qualification', label: 'Required Qualification', type: 'text', required: true },
        { name: 'year', label: 'Year', type: 'number', required: true }
    ],
    certificate: [
        { name: 'year', label: 'Year', type: 'number', required: true },
        { name: 'college', label: 'College Name', type: 'text', required: true },
        { name: 'qualification', label: 'Qualification', type: 'text', required: true }
    ],
    users: [
        { name: 'name', label: 'Full Name', type: 'text', required: true },
        { name: 'email', label: 'Email Address', type: 'email', required: true },
        { name: 'designation', label: 'Designation', type: 'text', placeholder: 'e.g., STUDENT, ADMIN', required: true }
    ]
};

// Select entity and update UI
function selectEntity(event, entity) {
    currentEntity = entity;
    
    // Update active button
    document.querySelectorAll('.btn-entity').forEach(btn => btn.classList.remove('active'));
    event.target.classList.add('active');
    
    // Update form and records
    renderForm();
    loadRecords();
    
    // Clear any previous messages
    clearMessage();
}

// Render form based on current entity
function renderForm() {
    const fields = entityFields[currentEntity];
    let html = '';
    
    fields.forEach(field => {
        html += `
            <div class="form-group">
                <label for="${field.name}">${field.label}</label>
                <input 
                    type="${field.type}" 
                    id="${field.name}"
                    name="${field.name}" 
                    ${field.placeholder ? `placeholder="${field.placeholder}"` : ''}
                    required
                >
            </div>
        `;
    });
    
    document.getElementById('formFields').innerHTML = html;
}

// Handle form submission for creating records
async function handleCreate(event) {
    event.preventDefault();
    
    // Get form data
    const formData = new FormData(event.target);
    const data = Object.fromEntries(formData);
    
    try {
        // Send POST request to create record
        const response = await fetch(`${apiUrl}/${currentEntity}`, {
            method: 'POST',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            const result = await response.json();
            showMessage(`✓ ${capitalizeEntity(currentEntity)} created successfully!`, 'success');
            event.target.reset();
            loadRecords();
        } else {
            const error = await response.text();
            showMessage(`✗ Error creating record: ${response.status}`, 'error');
        }
    } catch (error) {
        showMessage(`✗ Error: ${error.message}`, 'error');
        console.error('Create error:', error);
    }
}

// Load and display all records
async function loadRecords() {
    try {
        // Show loading state
        document.getElementById('records').innerHTML = '<p class="loading">Loading records...</p>';
        
        // Fetch records from API
        const response = await fetch(`${apiUrl}/${currentEntity}`, {
            method: 'GET',
            headers: { 'Accept': 'application/json' }
        });
        
        if (!response.ok) {
            throw new Error(`HTTP error! status: ${response.status}`);
        }
        
        const records = await response.json();
        
        // Display records
        if (records && records.length > 0) {
            let html = '';
            records.forEach(record => {
                html += renderRecord(record);
            });
            document.getElementById('records').innerHTML = html;
        } else {
            document.getElementById('records').innerHTML = '<p class="loading">No records found</p>';
        }
    } catch (error) {
        showMessage(`✗ Error loading records: ${error.message}`, 'error');
        console.error('Load error:', error);
        document.getElementById('records').innerHTML = '<p class="loading">Failed to load records</p>';
    }
}

// Render individual record with update/delete buttons
function renderRecord(record) {
    const idField = currentEntity === 'student' ? 'sid' : 'id';
    const recordId = record[idField];
    
    let html = `
        <div class="record-item">
            <p><strong>ID:</strong> ${recordId}</p>
    `;
    
    // Display all fields except ID
    Object.entries(record).forEach(([key, value]) => {
        if (key !== idField && key !== 'id') {
            const displayKey = formatFieldName(key);
            html += `<p><strong>${displayKey}:</strong> ${value || 'N/A'}</p>`;
        }
    });
    
    // Add action buttons with data attributes to avoid onclick issues
    html += `
        <div style="display: flex; gap: 8px; margin-top: 10px;">
            <button class="btn-edit" data-id="${recordId}" onclick="openEditForm(this)">✎ Edit</button>
            <button class="btn-delete-action" data-id="${recordId}" onclick="confirmDelete(this)">🗑 Delete</button>
        </div>
        </div>
    `;
    
    return html;
}

// Open edit form for record
function openEditForm(button) {
    const recordId = button.getAttribute('data-id');
    
    // Find the record in the DOM
    const recordItem = button.closest('.record-item');
    const fields = entityFields[currentEntity];
    
    // Create edit form
    let editHtml = `<div class="edit-modal" id="editModal_${recordId}">
        <div class="edit-form-container">
            <h3>Edit ${capitalizeEntity(currentEntity)}</h3>
            <form id="editForm_${recordId}" onsubmit="handleUpdate(event, ${recordId})">`;
    
    fields.forEach(field => {
        let currentValue = '';
        
        // Extract current value from record item
        const paragraphs = recordItem.querySelectorAll('p');
        for (let p of paragraphs) {
            if (p.textContent.includes(formatFieldName(field.name))) {
                currentValue = p.textContent.split(':')[1]?.trim() || '';
                break;
            }
        }
        
        editHtml += `
            <div class="form-group">
                <label for="edit_${field.name}">${field.label}</label>
                <input 
                    type="${field.type}" 
                    id="edit_${field.name}"
                    name="${field.name}" 
                    value="${currentValue}"
                    required
                >
            </div>
        `;
    });
    
    editHtml += `
            <div style="display: flex; gap: 10px; margin-top: 15px;">
                <button type="submit" class="btn-primary" style="width: auto; flex: 1;">Save Changes</button>
                <button type="button" class="btn-secondary" style="width: auto; flex: 1; margin-top: 0;" onclick="closeEditForm(${recordId})">Cancel</button>
            </div>
            </form>
        </div>
    </div>`;
    
    // Insert modal into the page
    document.body.insertAdjacentHTML('beforeend', editHtml);
    document.getElementById(`editModal_${recordId}`).style.display = 'flex';
}

// Close edit form
function closeEditForm(recordId) {
    const modal = document.getElementById(`editModal_${recordId}`);
    if (modal) {
        modal.remove();
    }
}

// Handle update submission
async function handleUpdate(event, recordId) {
    event.preventDefault();
    
    const form = event.target;
    const formData = new FormData(form);
    const data = Object.fromEntries(formData);
    
    // Add ID to data
    const idField = currentEntity === 'student' ? 'sid' : 'id';
    data[idField] = recordId;
    
    try {
        const response = await fetch(`${apiUrl}/${currentEntity}`, {
            method: 'PUT',
            headers: { 
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify(data)
        });
        
        if (response.ok) {
            showMessage(`✓ ${capitalizeEntity(currentEntity)} updated successfully!`, 'success');
            closeEditForm(recordId);
            loadRecords();
        } else {
            showMessage(`✗ Error updating record: ${response.status}`, 'error');
        }
    } catch (error) {
        showMessage(`✗ Error: ${error.message}`, 'error');
        console.error('Update error:', error);
    }
}

// Confirm delete
function confirmDelete(button) {
    const recordId = button.getAttribute('data-id');
    if (confirm(`Are you sure you want to delete this ${currentEntity} (ID: ${recordId})?\n\nThis action cannot be undone.`)) {
        deleteRecord(recordId);
    }
}

// Delete record by ID
async function deleteRecord(id) {
    try {
        const response = await fetch(`${apiUrl}/${currentEntity}/${id}`, {
            method: 'DELETE',
            headers: { 'Accept': 'application/json' }
        });
        
        if (response.ok || response.status === 204) {
            showMessage(`✓ ${capitalizeEntity(currentEntity)} deleted successfully!`, 'success');
            loadRecords();
        } else {
            showMessage(`✗ Error deleting record: ${response.status}`, 'error');
        }
    } catch (error) {
        showMessage(`✗ Error: ${error.message}`, 'error');
        console.error('Delete error:', error);
    }
}

// Show message with auto-hide
function showMessage(text, type) {
    const messageDiv = document.getElementById('message');
    messageDiv.textContent = text;
    messageDiv.className = `message ${type}`;
    
    // Auto-hide after 5 seconds
    setTimeout(() => {
        messageDiv.className = 'message';
    }, 5000);
}

// Clear message
function clearMessage() {
    const messageDiv = document.getElementById('message');
    messageDiv.className = 'message';
    messageDiv.textContent = '';
}

// Capitalize entity name for display
function capitalizeEntity(entity) {
    return entity.charAt(0).toUpperCase() + entity.slice(1);
}

// Format field names for display (e.g., "firstName" -> "First Name")
function formatFieldName(fieldName) {
    return fieldName
        .replace(/([A-Z])/g, ' $1')  // Add space before capital letters
        .replace(/^./, str => str.toUpperCase())  // Capitalize first letter
        .replace(/^_+/, '')  // Remove leading underscores
        .trim();
}

// Initialize on page load
document.addEventListener('DOMContentLoaded', function() {
    renderForm();
    loadRecords();
});
