const userForm = document.getElementById('user-form');
const userProfile = document.getElementById('user-profile');

userForm.addEventListener('submit', function(event) {
  event.preventDefault();

  const firstName = document.getElementById('first-name').value;
  const lastName = document.getElementById('last-name').value;
  const email = document.getElementById('email').value;
  const password = document.getElementById('password').value;
  const engineeringField = document.getElementById('engineering-field').value;
  const profilePicture = document.getElementById('profile-picture').files[0];
  const resume = document.getElementById('resume').files[0];
  const cv = document.getElementById('cv').files[0];

  if (!firstName || !lastName || !email || !password || !engineeringField) {
    console.log('Please fill in all fields');
    return;
  }

  let profileHtml = `
    <h2>${firstName} ${lastName}</h2>
    <p>Email: ${email}</p>
    <p>Engineering Field: ${engineeringField}</p>
  `;

  if (profilePicture) {
    profileHtml += `<p>Profile Picture: <a href="${URL.createObjectURL(profilePicture)}" target="_blank">${profilePicture.name}</a></p>`;
  }

  if (resume) {
    profileHtml += `<p>Resume: <a href="${URL.createObjectURL(resume)}" target="_blank">${resume.name}</a></p>`;
  }

  if (cv) {
    profileHtml += `<p>CV: <a href="${URL.createObjectURL(cv)}" target="_blank">${cv.name}</a></p>`;
  }

  profileHtml += `<button id="edit-button">Edit</button>`;

  userProfile.innerHTML = profileHtml;
  userProfile.style.display = 'block';
  userForm.style.display = 'none';

  const editButton = document.getElementById('edit-button');
  editButton.addEventListener('click', function() {
    userForm.style.display = 'block';
    userProfile.style.display = 'none';
  });

  userForm.reset();
});
