<html>
<head style="font-family: tahoma; margin-left: 14em;">
<title>Edit Landlord</title>
<meta name="layout" content="main" style="font-family: tahoma;" />
<style>
dd {
	text-align: left;
	margin-left: 5em;
	margin-top: 1em;
	font-family: tahoma;
}
</style>
</head>
<body>

	<h1 style="font-family: tahoma; margin-left: 13em">&nbsp&nbsp
		Change Password</h1>
	
	<g:message code="${flash.message}" args="${flash.args}"
		default="${flash.default}" style="margin-left: 2em; font-style: bold; font-color: #FF0000" />
		
		<br>
	<br>
	<g:form action="edited"
		style="font-family: tahoma; font-size: .9em;
                margin-left: 17em;">

		<dl>

			<dt>
				User Id:
				${user?.username}
			</dt>
			<dd>
				<g:hiddenField name="username" value="${user?.username}" />
				<g:hasErrors bean="${user}" field="username">
					<g:eachError bean="${user}" field="username">
						<p style="color: red;">
							<g:message error="${it}" />
						</p>
					</g:eachError>
				</g:hasErrors>
			</dd>

			<dt>First Name:</dt>
			<dd>
				<g:textField name="firstName" value="${user?.firstName}" />
				<g:hasErrors bean="${user}" field="firstName">
					<g:eachError bean="${user}" field="firstName">
						<p style="color: red;">
							<g:message error="${it}" />
						</p>
					</g:eachError>
				</g:hasErrors>
			</dd>

			<dt>Last Name:</dt>
			<dd>
				<g:textField name="lastName" value="${user?.lastName}" />
				<g:hasErrors bean="${user}" field="lastName">
					<g:eachError bean="${user}" field="lastName">
						<p style="color: red;">
							<g:message error="${it}" />
						</p>
					</g:eachError>
				</g:hasErrors>
			</dd>

			<dt>Password:</dt>
			<dd>
				<g:passwordField name="password" value="" />
				<g:hasErrors bean="${user}" field="password">
					<g:eachError bean="${user}" field="password">
						<p style="color: red;">
							<g:message error="${it}" />
						</p>
					</g:eachError>
				</g:hasErrors>
			</dd>



			<dt>Repeat Password:</dt>
			<dd>
				<g:passwordField name="passwordrepeat" value="" />
				<g:hasErrors bean="${user}" field="password">
					<g:eachError bean="${user}" field="password">
						<p style="color: red;">
							<g:message error="${it}" />
						</p>
					</g:eachError>
				</g:hasErrors>
			</dd>

			<dt>
				Email:
				${user?.email}
			</dt>
			<dd>
				<g:hiddenField name="email" value="${user?.email}" />
				<g:hasErrors bean="${user}" field="email">
					<g:eachError bean="${user}" field="email">
						<p style="color: red;">
							<g:message error="${it}" />
						</p>
					</g:eachError>
				</g:hasErrors>
			</dd>

			<dt>
				Phone Number:
				${user?.phone}
			</dt>
			<dd>
				<g:hiddenField name="phone" value="${user?.phone}" />
				<g:hasErrors bean="${user}" field="phone">
					<g:eachError bean="${user}" field="phone">
						<p style="color: red;">
							<g:message error="${it}" />
						</p>
					</g:eachError>
				</g:hasErrors>
			</dd>



			<br>
			<dt>
				<g:submitButton name="submit" value="Submit"
					style="font-family: tahoma;" />
			</dt>
		</dl>

	</g:form>

</body>
</html>