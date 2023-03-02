const userForm = document.getElementById('user-form');
const userProfile = document.getElementById('user-profile');
const editButton = document.createElement('button');
editButton.textContent = 'Edit';
editButton.addEventListener('click', editProfile);

function editProfile() {
  userForm.style.display = 'block';
  userProfile.style.display = 'none';
}

userForm.addEventListener('submit', function(event) {
  event.preventDefault();
  
  const firstName = document.getElementById('first-name').value;
  const lastName = document.getElementById('last-name').value;
  const email = document.getElementById('email').value;
  const phone = document.getElementById('phone').value;
  const engineeringField = document.getElementById('engineering-field').value;
  const resume = document.getElementById('resume').files[0];
  const cv = document.getElementById('cv').files[0];
  const profilePicture = document.getElementById('profile-picture').files[0];
  
  if (!firstName || !lastName || !email || !phone || !engineeringField) {
    console.log('Please fill in all required fields');
    return;
  }
  
  const profileHtml = `
    <h2>${firstName} ${lastName}</h2>
    <p>Email: ${email}</p>
    <p>Phone: ${phone}</p>
    <p>Engineering Field: ${engineeringField}</p>
    <p>Resume: ${resume ? resume.name : 'None'}</p>
    <p>Cover Letter: ${cv ? cv.name : 'None'}</p>
    <p>Profile Picture: ${profilePicture ? profilePicture.name : 'None'}</p>
  `;
  
  console.log(profileHtml);
  
  userProfile.innerHTML = profileHtml;
  userProfile.appendChild(editButton);
  userProfile.style.display = 'block';
  userForm.style.display = 'none';
  userForm.reset();
});
