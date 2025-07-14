<h1>Authors</h1>
<h3>Reem Hoisman, Alon Zargari</h3>
<p>Emails:<br>alonza@edu.jmc.ac.il<br>reemho@edu.jmc.ac.il</p>

<h1>General Description</h1>
<p>
This project is a full-stack web platform that connects customers with communication service providers. 
It is developed using Spring Boot, Spring Security, and Thymeleaf as the front-end templating engine , and session management to handle user activity and security. 
The platform allows providers to register as admins by submitting an application form and selecting 
an existing service category (categories are managed exclusively by the super admin and cannot be created manually).
Once a provider is approved, they can create and manage package deals, offering 3-month, 6-month, or yearly (open for expansion)
communication plans with custom pricing. Customers who register on the site can search for available packages based on category. 
The site show to the customer all packages from different providers, making it easier for users to compare options, 
purchase plans, leave reviews, and filter reviews based on positivity or relevance.
After purchasing a package, the customer gains access to a private chat page where they can directly communicate with the provider.
The platform includes several features such as chat, purchase confirmation messages, unread message indicators, advanced filtering tools, 
customizable provider profile pictures, payment tracking, and wallet that can be charged for shopping. 
The entire website is responsive and built with a focus on a clean, smooth user experience.
</p>

<section>
  <h1>Project Workflow</h1>
<p>
  When the program starts, it runs an initialization process that:
</p>
<ul>
  <li>Creates the super admin</li>
  <li>Adds default categories</li>
  <li>Defines available plan durations (3 months, 6 months, 1 year)</li>
</ul>

<p><strong>Super Admin default credentials:</strong></p>
<ul>
  <li>Username: <code>admin</code></li>
  <li>Password: <code>admin123</code></li>
</ul>
<p><strong>Admin (providers) default credentials you can use to add packages:</strong></p>
<ul>
  <li>Username: <code>WaveTel</code>(CELLULAR)</li>
  <li>Password: <code>1</code></li>
  <li>Username: <code>SuperTV</code>(TELEVISION)</li>
  <li>Password: <code>1</code></li>
</ul>
<p><strong>users default credentials you can login with to find packages:</strong></p>
<ul>
  <li>Username: <code>alon</code></li>
  <li>Password: <code>1</code></li>
  <li>Username: <code>reem</code></li>
  <li>Password: <code>1</code></li>
</ul>
  <ol>
    <li>When the program starts, the super admin is created. He can add categories to control what categories exist and stop others from adding random ones.</li>
    <li>Providers can sign up and wait for the super admin to approve them as admins.</li>
    <li>The super admin checks and approves the admins.</li>
    <li>Admins add packages with descriptions and prices for 3-month, 6-month, or 1-year plans.</li>
    <li>Regular users can sign up and log in as customers.</li>
    <li>Customers can search for packages, read and write reviews, and add packages to their shopping cart. To buy, they need to add money to their wallet first.</li>
    <li>After adding money, customers can go to the cart to buy or remove packages.</li>
    <li>After buying, customers can chat with the provider on the chat page.</li>
  </ol>
</section>
<p>
</p>
<h1>Project Folder</h1>
<ul>
  <li><b>src folder</b> – Contains all the project's components to run
    <ul>
      <li><b>main folder</b>
        <ul>
          <li><b>java</b>
            <ul>
              <li><b>components</b> – Contains Spring <code>@Component</code> classes for state management and session-scoped data</li>
              <li><b>configuration</b> – Configurations for WebSocket, cart session scope, and search result request scope</li>
              <li><b>constants</b> – Program constants</li>
              <li><b>controller</b> – All controllers for navigation and request handling</li>
              <li><b>dto</b> – Data Transfer Objects used to encapsulate and transfer data between different views</li>
              <li><b>entity</b> – Represents all entities mapped to the database tables</li>
              <li><b>repository</b> – Responsible for directly retrieving and saving data to the database</li>
              <li><b>service</b> – Services that receive data from repositories and process it to match client requirements</li>
              <li><b>validator</b> – Custom validators (e.g., for image validation)</li>
              <li><b>ApplicationConfig</b> – Java class for application configuration and Spring filter chain setup</li>
              <li><b>Ex4Application</b> – Main class annotated with <code>@SpringBootApplication</code></li>
              <li><b>MyUserPrincipal</b> – Connects User data to Spring Security, allowing access to the logged-in user via <code>@AuthenticationPrincipal</code></li>
            </ul>
          </li>
          <li><b>resources</b>
            <ul>
              <li><b>static</b> – Static resources
                <ul>
                  <li><b>css</b> – Stylesheets for site design</li>
                  <li><b>js</b> – JavaScript files:
                    <ul>
                      <li><code>chat.js</code> – Connects to WebSocket and displays chat messages</li>
                      <li><code>messageToast.js</code> – Shows toast messages for cart actions</li>
                    </ul>
                  </li>
                </ul>
              </li>
              <li><b>templates</b> – Thymeleaf templates divided by user roles and purposes:
                <ul>
                  <li><b>admin</b> – Templates visible only to admins</li>
                  <li><b>user</b> – Templates visible only to users</li>
                  <li><b>super_admin</b> – Templates visible only to super admins</li>
                  <li><b>shared</b> – Templates visible to all, with role-based variations</li>
                  <li><b>fragments</b> – Reusable common template parts</li>
                  <li><b>general</b> – Templates for login, registration, and error pages</li>
                </ul>
              </li>
              <li><b>application.properties</b> – Project configuration properties</li>
            </ul>
          </li>
        </ul>
      </li>
    </ul>
  </li>
  <li><b>README.md</b> – Project documentation</li>
  <li><b>Other general files</b> – Such as <code>.gitignore</code>, <code>pom.xml</code></li>
</ul>
<h1>Notes</h1>
<ol>
  <li>Many features and buttons on the website are not fully implemented yet and have been left for future expansion. The working features are marked in the project workflow, so please follow the workflow accordingly.</li>
  <li>The program uses sessions – if you are inactive for the set time, you will not be able to perform actions once the session expires.</li>
  <li>The platform is designed to be extendable, not only for communication providers but also for other types of services. This was the motivation behind choosing this project.</li>
  <li><strong> we used  one of the 3 days extra to fix readme</strong></li>
</ol>
<h1>Execution</h1>
<ol>
  <li>Start the XAMPP application.</li>
  <li>Click "Start" for both Apache and MySQL.</li>
  <li>Click on "Admin" in the MySQL tab.</li>
  <li>In phpMyAdmin, create a new database named <code>ex4</code>.</li>
  <li>Run the application in your IDE by clicking the "Run" button.</li>
</ol>
