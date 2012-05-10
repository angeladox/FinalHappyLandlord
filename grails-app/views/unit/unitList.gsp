<html>
<head>
<title>Tenants for ${landlord.firstName}
</title>
<meta name="layout" content="main" />
</head>
<body>


	<g:if test="${tenantAmt == 0}">
		<h1 style="margin-left: 1.5em;">
			${landlord.firstName}: You currently have
			${unitAmt}
			tenants. Do you need to
			<g:link controller="tenant" action="create">      
      add one      
              </g:link>
			?
		</h1>
	</g:if>
	<g:else>

		<h1 style="margin-left: 1.5em;">
			Units for
			${landlord.firstName}
		</h1>
	</g:else>
	<br>
	<div class="allTens" width="30px">
		<table width="30px" style = "font-size: .8em;">
			<g:each in="${units}" var="unit">
				<g:each in="${unit}" var="un">
					<tr width="30px">
						<td>Unit Number:
							${un.unitNo}
						</td>
						<td>Square Footage:
							${un.sqFeet}
						</td>
						<td>Description:
							${un.description}
						</td>
						<td>Bedrooms:
							${un.bedrooms}
						</td>
						<td>Status:
							${un.status}
						</td>
						
						
						</tr>
				</g:each>
			</g:each>
			
		</table>
	</div>
</body>
</html>

