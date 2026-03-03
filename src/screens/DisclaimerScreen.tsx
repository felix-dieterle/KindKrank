import React from 'react';
import {
  View,
  Text,
  ScrollView,
  TouchableOpacity,
  StyleSheet,
  SafeAreaView,
} from 'react-native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import { RootStackParamList } from '../types';

type Props = {
  navigation: NativeStackNavigationProp<RootStackParamList, 'Disclaimer'>;
};

export default function DisclaimerScreen({ navigation }: Props) {
  return (
    <SafeAreaView style={styles.container}>
      <ScrollView contentContainerStyle={styles.content}>
        <Text style={styles.title}>⚠️ Rechtlicher Hinweis</Text>
        <Text style={styles.subtitle}>Haftungsausschluss</Text>

        <View style={styles.box}>
          <Text style={styles.bodyText}>
            Diese App bietet <Text style={styles.bold}>allgemeine Informationen</Text> zur
            Vorgehensweise bei Erkrankung eines Kindes in Deutschland. Sie ersetzt{' '}
            <Text style={styles.bold}>keine Rechts- oder Steuerberatung</Text> und stellt keine
            rechtliche Empfehlung dar.
          </Text>

          <Text style={styles.bodyText}>
            Alle Angaben wurden sorgfältig zusammengestellt, jedoch können sich gesetzliche
            Regelungen, Krankenkassenrichtlinien oder Unternehmensregelungen jederzeit ändern. Für
            die Richtigkeit, Vollständigkeit und Aktualität der bereitgestellten Informationen wird{' '}
            <Text style={styles.bold}>keine Haftung übernommen</Text>.
          </Text>

          <Text style={styles.bodyText}>
            Die Nutzung dieser App erfolgt auf eigene Verantwortung. Für Entscheidungen, die auf
            Basis der hier dargestellten Informationen getroffen werden, übernehmen Entwickler und
            Betreiber dieser App <Text style={styles.bold}>keinerlei Haftung</Text>. Bei
            Unsicherheiten wenden Sie sich bitte an Ihre Krankenkasse, Ihren Arbeitgeber oder einen
            Fachanwalt für Arbeitsrecht.
          </Text>

          <Text style={styles.bodyText}>
            Diese App unterstützt Sie dabei, den <Text style={styles.bold}>offiziellen Weg</Text>{' '}
            zu gehen. Ein bewusstes Umgehen gesetzlicher Regelungen wird ausdrücklich nicht
            empfohlen.
          </Text>

          <Text style={styles.legalNote}>
            Gemäß § 675 BGB und den allgemeinen Grundsätzen der Haftungsbeschränkung für
            unentgeltliche Informationsleistungen ist eine Haftung für die Inhalte dieser App
            ausgeschlossen, soweit keine vorsätzliche oder grob fahrlässige Pflichtverletzung
            vorliegt.
          </Text>
        </View>

        <TouchableOpacity
          style={styles.button}
          onPress={() => navigation.replace('Home')}
          accessibilityLabel="Hinweis verstanden und App starten"
        >
          <Text style={styles.buttonText}>Verstanden – App starten</Text>
        </TouchableOpacity>
      </ScrollView>
    </SafeAreaView>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#FFF8E1',
  },
  content: {
    padding: 20,
    paddingBottom: 40,
  },
  title: {
    fontSize: 26,
    fontWeight: 'bold',
    color: '#D32F2F',
    textAlign: 'center',
    marginBottom: 6,
    marginTop: 20,
  },
  subtitle: {
    fontSize: 18,
    fontWeight: '600',
    color: '#555',
    textAlign: 'center',
    marginBottom: 20,
  },
  box: {
    backgroundColor: '#fff',
    borderRadius: 12,
    padding: 18,
    borderLeftWidth: 4,
    borderLeftColor: '#D32F2F',
    marginBottom: 24,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 3,
  },
  bodyText: {
    fontSize: 15,
    color: '#333',
    lineHeight: 24,
    marginBottom: 14,
  },
  bold: {
    fontWeight: 'bold',
  },
  legalNote: {
    fontSize: 12,
    color: '#888',
    lineHeight: 18,
    fontStyle: 'italic',
    marginTop: 6,
  },
  button: {
    backgroundColor: '#2E86AB',
    borderRadius: 12,
    paddingVertical: 16,
    alignItems: 'center',
  },
  buttonText: {
    color: '#fff',
    fontSize: 17,
    fontWeight: 'bold',
  },
});
